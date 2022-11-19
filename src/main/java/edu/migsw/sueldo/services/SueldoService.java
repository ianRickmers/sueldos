package edu.migsw.sueldo.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.migsw.sueldo.entities.SueldoEntity;
import edu.migsw.sueldo.models.HoraExtra;
import edu.migsw.sueldo.entities.DescuentoEntity;
import edu.migsw.sueldo.entities.EmpleadoEntity;
import edu.migsw.sueldo.repositories.DescuentoRepository;
import edu.migsw.sueldo.repositories.EmpleadoRepository;
import edu.migsw.sueldo.repositories.SueldoRepository;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SueldoService {

    @Autowired
    SueldoRepository sueldosRepository;

    @Autowired
    EmpleadoRepository empleadoRepository;

    @Autowired
    DescuentoRepository descuentoRepository;

    @Autowired
    RestTemplate restTemplate;

    public void deleteAll() {
        sueldosRepository.deleteAll();
    }
    
    public List<SueldoEntity> getAll() {
        return sueldosRepository.findAll();
    }

    public List<SueldoEntity> getByRut(String rut) {
        return sueldosRepository.findByRut(rut);
    }

    public Optional<SueldoEntity> getSueldoById(Long id) {
        return sueldosRepository.findById(id);
    }
    public SueldoEntity save(SueldoEntity sueldo) {
        SueldoEntity sueldoNueva=sueldosRepository.save(sueldo);
        return sueldoNueva;
    }

    public Integer calcularSueldoBase(EmpleadoEntity empleado) {
        String categoria = empleado.getCategoria();
        Integer sueldoBase = 0;
        if (categoria.equals("A")) {
            sueldoBase = 1700000;
            return sueldoBase;
        } else if (categoria.equals("B")) {
            sueldoBase = 1200000;
            return sueldoBase;
        } else if (categoria.equals("C")) {
            sueldoBase = 800000;
            return sueldoBase;
        }
        return sueldoBase;
    }

    // Calcula la bonificacion por antiguedad de un empleado
    public ArrayList<Integer> calcularBonificacionTiempoServicio(EmpleadoEntity empleado, SueldoEntity sueldo, Date fechaActual)
            throws ParseException {
        Integer bonificacion = 0;
        ArrayList<Integer> anosAndBono = new ArrayList<>();
        Date fechaIngreso = new SimpleDateFormat("yyyy-MM-dd").parse(empleado.getFechaIngreso());
        Integer diff = (int) ((TimeUnit.DAYS.convert((fechaActual.getTime() - fechaIngreso.getTime()),
                TimeUnit.MILLISECONDS)) / 365);
        if (diff < 5) {
            bonificacion = 0;
            anosAndBono.add(diff);
            anosAndBono.add(bonificacion);
            return anosAndBono;
        } else if (diff >= 5 && diff < 10) {
            bonificacion = (int) Math.round(sueldo.getSueldoFijo() * 0.05);
            anosAndBono.add(diff);
            anosAndBono.add(bonificacion);
            return anosAndBono;
        } else if (diff >= 10 && diff < 15) {
            bonificacion = (int) Math.round(sueldo.getSueldoFijo() * 0.08);
            anosAndBono.add(diff);
            anosAndBono.add(bonificacion);
            return anosAndBono;
        } else if (diff >= 15 && diff < 20) {
            bonificacion = (int) Math.round(sueldo.getSueldoFijo() * 0.11);
            anosAndBono.add(diff);
            anosAndBono.add(bonificacion);
            return anosAndBono;
        } else if (diff >= 20 && diff < 25) {
            bonificacion = (int) Math.round(sueldo.getSueldoFijo() * 0.14);
            anosAndBono.add(diff);
            anosAndBono.add(bonificacion);
            return anosAndBono;
        } else if (diff >= 25) {
            bonificacion = (int) Math.round(sueldo.getSueldoFijo() * 0.17);
            anosAndBono.add(diff);
            anosAndBono.add(bonificacion);
            return anosAndBono;
        }
        return anosAndBono;
    }

    public SueldoEntity montoHorasExtra( HoraExtra horaExtra, SueldoEntity sueldo) {
        if (horaExtra.getAutorizada() == 0) {
            sueldo.setMontoHorasExtra(0);
            return sueldo;
        }
        if (horaExtra.getAutorizada() == 1) {
            String categoria = sueldo.getCategoria();
            if (categoria.equals("A")) {
                sueldo.setMontoHorasExtra(horaExtra.getCantidadHoras() * 25000);
                return sueldo;
            } else if (categoria.equals("B")) {
                sueldo.setMontoHorasExtra(horaExtra.getCantidadHoras() * 20000);
                return sueldo;
            } else if (categoria.equals("C")) {
                sueldo.setMontoHorasExtra(horaExtra.getCantidadHoras() * 10000);
                return sueldo;
            }
        }
        return sueldo;
    }

    public SueldoEntity montoDescuentos(Integer inasistencias, DescuentoEntity descuento, SueldoEntity sueldo) {
        Integer sueldoBase = sueldo.getSueldoFijo();
        double montoDescuento10 = descuento.getDesc10() * 0.01 * sueldoBase;
        double montoDescuento25 = descuento.getDesc25() * 0.03 * sueldoBase;
        double montoDescuento45 = descuento.getDesc45() * 0.06 * sueldoBase;
        Integer montoDescuento = (int) Math
                .round((inasistencias * 0.15 * sueldoBase) + montoDescuento10 + montoDescuento25 + montoDescuento45);
        sueldo.setMontoDescuentos(montoDescuento);
        return sueldo;
    }

    public SueldoEntity calcularSueldoNeto(SueldoEntity sueldo) {
        Integer sueldoNeto = sueldo.getSueldoFijo() + sueldo.getBonificacionAnosServicio() + sueldo.getMontoHorasExtra()
                - sueldo.getMontoDescuentos();
        sueldo.setSueldoBruto(sueldoNeto);
        return sueldo;
    }

    public SueldoEntity calcularCotizacionPrevisional(SueldoEntity sueldo) {
        Integer cotizacionPrevisional = (int) Math.round(sueldo.getSueldoBruto() * 0.1);
        sueldo.setCotizacionPrevisional(cotizacionPrevisional);
        return sueldo;
    }

    public SueldoEntity calcularCotizacionSalud(SueldoEntity sueldo) {
        Integer cotizacionSalud = (int) Math.round(sueldo.getSueldoBruto() * 0.08);
        sueldo.setCotizacionSalud(cotizacionSalud);
        return sueldo;
    }

    public SueldoEntity calcularMontoFinal(SueldoEntity sueldo) {
        Integer montoFinal = sueldo.getSueldoBruto() - sueldo.getCotizacionPrevisional() - sueldo.getCotizacionSalud();
        sueldo.setMontoFinal(montoFinal);
        return sueldo;
    }

    public String upload() throws ParseException{
        sueldosRepository.deleteAll();
        ArrayList<EmpleadoEntity> empleados = (ArrayList<EmpleadoEntity>) empleadoRepository.findAll();
        for(EmpleadoEntity empleado:empleados){
            String rut=empleado.getRut();
            SueldoEntity sueldo=new SueldoEntity(null,empleado.getRut(),empleado.getNombres(),empleado.getApellidos(),empleado.getCategoria(),0,0,0,0,0,0,0,0,0);
            sueldo.setSueldoFijo(calcularSueldoBase(empleado));
            Date fechaActual=new Date();
            ArrayList<Integer> bonoAndSueldo=calcularBonificacionTiempoServicio(empleado, sueldo, fechaActual);
            sueldo.setAnosServicio(bonoAndSueldo.get(0));
            sueldo.setBonificacionAnosServicio(bonoAndSueldo.get(1));
            HoraExtra[] horasExtras=restTemplate.getForObject("http://localhost:8010/horaextra/byrut/"+rut, HoraExtra[].class);
            if(horasExtras==null)
                return "No hay horas extras";
            ArrayList<DescuentoEntity> descuentos= descuentoRepository.findByRut(rut);
            if (horasExtras.length!=0){
                sueldo=montoHorasExtra(horasExtras[0],sueldo);
            }else{
                return "Error leyendo horas extras";
            }
            if (!descuentos.isEmpty()){
                Integer inasistencias=restTemplate.getForObject("http://localhost:8009/inasistencias/countbyrut/"+rut, Integer.class);
                sueldo=montoDescuentos(inasistencias,descuentos.get(0),sueldo);
            }else{
                return "Error leyendo descuentos";
            }
            sueldo=calcularSueldoNeto(sueldo);
            sueldo=calcularCotizacionPrevisional(sueldo);
            sueldo=calcularCotizacionSalud(sueldo);
            sueldo=calcularMontoFinal(sueldo);
            sueldosRepository.save(sueldo);
        }
        return "Sueldos calculados correctamente";
    }
}

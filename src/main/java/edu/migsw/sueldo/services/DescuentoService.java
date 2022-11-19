package edu.migsw.sueldo.services;

import edu.migsw.sueldo.entities.DescuentoEntity;
import edu.migsw.sueldo.entities.EmpleadoEntity;

import edu.migsw.sueldo.models.Marca;
import edu.migsw.sueldo.models.Inasistencia;

import edu.migsw.sueldo.repositories.DescuentoRepository;
import edu.migsw.sueldo.repositories.EmpleadoRepository;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DescuentoService {
    
    @Autowired DescuentoRepository descuentoRepository;
    @Autowired EmpleadoRepository empleadoRepository;

    @Autowired RestTemplate restTemplate;

    private DescuentoEntity cambiarDesc10(DescuentoEntity descuento){
        descuento.setDesc10(descuento.getDesc10()+1);
        return descuento;
    }

    private DescuentoEntity cambiarDesc25(DescuentoEntity descuento){
        descuento.setDesc25(descuento.getDesc25()+1);
        return descuento;
    }

    private DescuentoEntity cambiarDesc45(DescuentoEntity descuento){
        descuento.setDesc45(descuento.getDesc45()+1);
        return descuento;
    }

    //Para hacer los test unitarios cambiar a retorno del descuento cambiado
    private DescuentoEntity cambiarDescuentos(Integer marcaHora, Integer marcaMinuto, DescuentoEntity descuento){
        if(marcaHora==8 && marcaMinuto>10 && marcaMinuto<=25){
            cambiarDesc10(descuento);
            return descuento;
        }
        if(marcaHora==8 && marcaMinuto>25 && marcaMinuto<=45){
            cambiarDesc25(descuento);
            return descuento;
        }
        if(marcaHora==8 && marcaMinuto>45 && marcaMinuto<=59){
            cambiarDesc45(descuento);
            return descuento;
        }
        if(marcaHora==9 && marcaMinuto>=0 && marcaMinuto<=10){
            cambiarDesc45(descuento);
            return descuento;
        }
        return descuento;
    }

    public boolean seDebeCrearInasistencia(Integer marcaHora, Integer marcaMinuto){
        if(marcaHora==9 && marcaMinuto>10 || marcaHora>9){
            return true;
        }
        return false;
    }

    public String calcularDescuentos(){
        descuentoRepository.deleteAll();
        ArrayList<EmpleadoEntity>empleados=(ArrayList<EmpleadoEntity>) empleadoRepository.findAll();
        for(EmpleadoEntity empleado:empleados){
            String rut=empleado.getRut();
            Marca[] marcasRut=restTemplate.getForObject("http://localhost:8008/marcas/byrut/"+rut, Marca[].class);
            if(marcasRut==null){
                return "No se pudo calcular los descuentos";
            }
            int n = marcasRut.length;
            for(int i=0;i<n;i+=2){
                int marcaHora=Integer.parseInt((marcasRut[i]).getHora());
                int marcaMinuto=Integer.parseInt((marcasRut[i]).getMinuto());
                ArrayList<DescuentoEntity>descuentos=descuentoRepository.findByRut(rut);
                if(!descuentos.isEmpty()){
                    DescuentoEntity descuento=descuentos.get(0);
                    cambiarDescuentos(marcaHora, marcaMinuto,descuento);
                    descuentoRepository.save(descuento);
                }
                else{
                    DescuentoEntity descuento=new DescuentoEntity(null,rut,0,0,0);
                    cambiarDescuentos(marcaHora, marcaMinuto,descuento);
                    descuentoRepository.save(descuento);
                }
                String fecha=(marcasRut[i]).getFecha();
                Integer cantidadInasistencias=restTemplate.getForObject("http://localhost:8009/inasistencias/byrutdate/"+rut+"/"+fecha, Integer.class);
                if(seDebeCrearInasistencia(marcaHora, marcaMinuto) && cantidadInasistencias == 0){
                    Inasistencia inasistencia=new Inasistencia(null,rut,fecha, 0);
                    HttpEntity<Inasistencia> request = new HttpEntity<Inasistencia>(inasistencia);
                    restTemplate.postForObject("http://localhost:8009/inasistencias", request, Inasistencia.class);
                }
            }
        }
        return "Se calcularon los descuentos correctamente";
    }
}

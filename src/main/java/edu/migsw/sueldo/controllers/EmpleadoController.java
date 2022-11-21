package edu.migsw.sueldo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.migsw.sueldo.repositories.EmpleadoRepository;
import edu.migsw.sueldo.entities.EmpleadoEntity;

@RestController
@RequestMapping("/empleado")
public class EmpleadoController {

    @Autowired EmpleadoRepository empleadoRepository;

    @GetMapping
    public ResponseEntity<List<EmpleadoEntity>> getAll(){
        List<EmpleadoEntity> empleados = empleadoRepository.findAll();
        if(empleados.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(empleados);
    }
    @PostMapping
    public ResponseEntity<EmpleadoEntity> save(@RequestBody EmpleadoEntity Inasistencia){
        EmpleadoEntity entidadGuardada = empleadoRepository.save(Inasistencia);
        return ResponseEntity.ok(entidadGuardada);
    }

    @GetMapping("/delete/{rut}")
    public String delete(@PathVariable String rut){
        EmpleadoEntity empleado = empleadoRepository.findByRut(rut);
        if(empleado == null){
            return "No existe el empleado";
        }
        empleadoRepository.delete(empleado);
        return "Empleado eliminado";
    }
}

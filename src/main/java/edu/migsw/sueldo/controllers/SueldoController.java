package edu.migsw.sueldo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import edu.migsw.sueldo.entities.SueldoEntity;
import edu.migsw.sueldo.services.SueldoService;

import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/sueldos")
public class SueldoController {
    
    @Autowired
    SueldoService sueldoService;

    @GetMapping
    public ResponseEntity<List<SueldoEntity>> getAll(){
        List<SueldoEntity> sueldos = sueldoService.getAll();
        if(sueldos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sueldos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SueldoEntity> getSueldoById(@PathVariable("id") Long id){
        SueldoEntity sueldo = sueldoService.getSueldoById(id).get();
        if(sueldo == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sueldo);
    }

    @PostMapping()
    public ResponseEntity<SueldoEntity> save(@RequestBody SueldoEntity sueldo){
        SueldoEntity sueldoGuardada = sueldoService.save(sueldo);
        return ResponseEntity.ok(sueldoGuardada);
    }

    @GetMapping("/byrut/{rut}")
    public ResponseEntity<List<SueldoEntity>> getByRut(@PathVariable("rut") String rut){
        List<SueldoEntity> sueldos = sueldoService.getByRut(rut);
        if(sueldos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sueldos);
    }

    @GetMapping("/calcular")
    public ResponseEntity<String> calcularSueldos() throws ParseException{
        String sueldos = sueldoService.upload();
        return ResponseEntity.ok(sueldos);
    }
}

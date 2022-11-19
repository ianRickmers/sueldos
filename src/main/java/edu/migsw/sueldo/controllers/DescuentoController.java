package edu.migsw.sueldo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import edu.migsw.sueldo.services.DescuentoService;

import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/descuento")
@CrossOrigin("http://localhost:3000")
public class DescuentoController {
    
    @Autowired
    DescuentoService DescuentoService;

    @GetMapping
    public ResponseEntity<String> calcularDescuentos(){
        String descuentos = DescuentoService.calcularDescuentos();
        return ResponseEntity.ok(descuentos);
    }

    /* @GetMapping
    public ResponseEntity<List<DescuentoEntity>> getAll(){
        List<DescuentoEntity> Descuentos = DescuentoService.getAll();
        if(Descuentos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(Descuentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DescuentoEntity> getDescuentoById(@PathVariable("id") Long id){
        DescuentoEntity Descuento = DescuentoService.getDescuentoById(id).get();
        if(Descuento == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Descuento);
    }

    @PostMapping()
    public ResponseEntity<DescuentoEntity> save(@RequestBody DescuentoEntity Descuento){
        DescuentoEntity DescuentoGuardada = DescuentoService.save(Descuento);
        return ResponseEntity.ok(DescuentoGuardada);
    }

    @GetMapping("/byrut/{rut}")
    public ResponseEntity<List<DescuentoEntity>> getByRut(@PathVariable("rut") String rut){
        List<DescuentoEntity> Descuentos = DescuentoService.getByRut(rut);
        if(Descuentos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(Descuentos);
    } */

}


package edu.migsw.sueldo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inasistencia {

    private Long id;
    
    private String rut;
    private String fecha;
    private Integer justificada = 0;
}
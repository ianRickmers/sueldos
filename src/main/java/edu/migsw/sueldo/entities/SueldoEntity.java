package edu.migsw.sueldo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "sueldo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SueldoEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String rut;
    private String nombres;
    private String apellidos;
    private String categoria;
    private Integer anosServicio;
    private Integer sueldoFijo;
    private Integer bonificacionAnosServicio;
    private Integer montoHorasExtra;
    private Integer montoDescuentos;
    private Integer sueldoBruto;
    private Integer cotizacionPrevisional;
    private Integer cotizacionSalud;
    private Integer montoFinal;
}
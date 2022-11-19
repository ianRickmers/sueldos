package edu.migsw.sueldo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "descuentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DescuentoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    
    private String rut;
    private int desc10=0;
    private int desc25=0;
    private int desc45=0;
}

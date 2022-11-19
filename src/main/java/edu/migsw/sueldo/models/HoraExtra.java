package edu.migsw.sueldo.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoraExtra {
    private Long id;

    private String rut;
    private Integer cantidadHoras;
    private Integer cantidadMinutos;
    private Integer autorizada = 0;
}

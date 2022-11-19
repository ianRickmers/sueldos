package edu.migsw.sueldo.repositories;

import edu.migsw.sueldo.entities.DescuentoEntity;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescuentoRepository extends JpaRepository<DescuentoEntity, Long> {
    public ArrayList<DescuentoEntity> findByRut(String rut);
}

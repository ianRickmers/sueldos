package edu.migsw.sueldo.repositories;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.migsw.sueldo.entities.SueldoEntity;

@Repository
public interface SueldoRepository extends JpaRepository<SueldoEntity, Long> {

    @Query("SELECT m from SueldoEntity m WHERE m.rut = :rut")
    ArrayList<SueldoEntity> findByRut(@Param("rut") String rut);

    @Query("SELECT m from SueldoEntity m WHERE m.id = :id")
    Optional<SueldoEntity> findById(@Param("id") Long id);
}
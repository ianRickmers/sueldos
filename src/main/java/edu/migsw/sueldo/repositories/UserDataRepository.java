package edu.migsw.sueldo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.migsw.sueldo.entities.UserData;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Integer> {
    UserData findByUsuario(String username);
}
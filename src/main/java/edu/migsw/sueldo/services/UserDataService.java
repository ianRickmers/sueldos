package edu.migsw.sueldo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.migsw.sueldo.entities.UserData;
import edu.migsw.sueldo.repositories.UserDataRepository;

import java.util.List;

@Service
public class UserDataService {

    @Autowired
    UserDataRepository userDataRepository;

    public List<UserData> getAll() {
        return userDataRepository.findAll();
    }

    public UserData getUserById(int id) {
        return userDataRepository.findById(id).orElse(null);
    }

    public UserData save(UserData userData) {
        UserData userDataNew = userDataRepository.save(userData);
        return userDataNew;
    }

}


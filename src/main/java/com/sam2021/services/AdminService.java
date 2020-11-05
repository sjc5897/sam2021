package com.sam2021.services;

import com.sam2021.database.UserEntity;
import com.sam2021.database.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    UserRepository userRepository;

    public List<UserEntity> getUsers(){
        return userRepository.findAll();
    }
}

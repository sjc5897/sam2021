package com.sam2021.services;


import com.sam2021.database.UserEntity;
import com.sam2021.database.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegistrationService {

    private final UserRepository userRepository;

    public RegistrationService(
            @Autowired UserRepository userRepository
    ) {
        this.userRepository = userRepository;
        // userRepository.save(new UserEntity());
    }
    public boolean register(String email, String password, String firstname, String lastname, String role) {
        userRepository.save(new UserEntity(email,password,firstname,lastname,role));
        return true;
    }
}
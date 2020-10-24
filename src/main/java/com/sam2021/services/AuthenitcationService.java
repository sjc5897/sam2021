package com.sam2021.services;

import org.springframework.stereotype.Service;

/**
 * The service for Login, this assists the controller in authentication
 * Language: Java 13
 * Framework: Spring
 * Author: Stephen Cook <sjc5897@rit.edu>
 * Created: 10/24/20
 * Last Edit: 10/24/20
 */
@Service
public class AuthenitcationService {
    /**
     * This will assist the controller in user Authentication, currently spoofed
     * @param uid   String representing the user id
     * @param pw    String representing the user password
     * @return      Boolean representing if the user is authenticated
     */
    public boolean validateUser(String uid, String pw){
        // TODO: Implement validation for real
        return uid.equals("Stephen") && pw.equals("abc123");
    }
}

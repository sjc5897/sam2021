package com.sam2021.controller;

import com.sam2021.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static com.sam2021.security.Hasher.hashPass;

/**
 * This the controller for the registration page.
 * Language: Java 13
 * Framework: Spring
 * Author: Stephen Cook <sjc5897@rit.edu>
 * Created: 10/31/20 Happy Halloween
 * Last Edit: 10/31/20
 */
@Controller
public class RegistrationController {
    @Autowired
    RegistrationService service;

    @RequestMapping(value="/register", method = RequestMethod.GET)
    public String displayRegistrationPage(){
        return "register";
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public String handleRegistration(@RequestParam(name="email") String email,
                                     @RequestParam(name="password") String password,
                                     @RequestParam(name="f_name") String f_name,
                                     @RequestParam(name="l_name") String l_name){
        password = hashPass(password);
        boolean suc= service.register(email,password,f_name,l_name,"author");
        if(suc){
            return "login";
        }
        else{
            return "register";
        }
    }

}

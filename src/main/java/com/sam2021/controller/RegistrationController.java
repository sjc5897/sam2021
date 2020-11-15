package com.sam2021.controller;

import com.sam2021.database.UserEntity;
import com.sam2021.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * This the controller for the registration page.
 * Language: Java 13
 * Framework: Spring
 * Author: Stephen Cook <sjc5897@rit.edu>
 * Created: 10/31/20 Happy Halloween
 * Last Edit: 11/15/20
 */
@Controller
public class RegistrationController {
    // service
    @Autowired
    private UserService service;

    /**
     * Gets the registration page
     * @return  String for register page
     */
    @RequestMapping(value="/register", method = RequestMethod.GET)
    public String displayRegistrationPage(){
        return "register";
    }


    /**
     * Handles registration form
     * @param email         String for user email
     * @param password      String for user password
     * @param f_name        String for user f_name
     * @param l_name        String for user l_name
     * @param model         Model
     * @return              String representing redirect
     */
    @RequestMapping(value="/register", method = RequestMethod.POST)
    public String handleRegistration(@RequestParam(name="email") String email,
                                     @RequestParam(name="pw") String password,
                                     @RequestParam(name="f_name") String f_name,
                                     @RequestParam(name="l_name") String l_name, Model model){
        password = service.hashPass(password);
        boolean suc= service.updateUser(new UserEntity(email,password,f_name ,l_name,"author" ));
        if(suc){
            model.addAttribute("reg_suc",true);
            return "redirect:login";
        }
        else{
            model.addAttribute("reg_suc", false);
            return "register";
        }
    }

}

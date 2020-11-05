package com.sam2021.controller;

import com.sam2021.database.UserEntity;
import com.sam2021.services.AuthenitcationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static com.sam2021.security.Hasher.hashPass;

/**
 * This is a custom controller for login into the system
 * Note: For some reason IDE wouldn't let me name it LoginController
 * Language: Java 13
 * Framework: Spring
 * Author: Stephen Cook <sjc5897@rit.edu>
 * Created: 10/24/20
 * Last Edit: 10/24/20
 */
@Controller
public class AuthenticationController {
    // This wires us to the associated service
    @Autowired
    AuthenitcationService service;

    /**
     * This displays a page when "/" is requested, should link to home but I was lazy
     * TODO: Remove this and have an actual home or index page
     * @return String representing loading of login.html
     */
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String displayIndexPage(){
        return "login";
    }

    /**
     * This handles GET requests for /login, simply displays login
     * @return String representing loading of login.html
     */
    @RequestMapping(value="/login", method= RequestMethod.GET)
    public String displayLoginPage(){
        return "login";
    }

    /**
     * Handles POST requests to the login page, Authenticates the user
     * @param name  Requested param from form, typically email, but is user id
     * @param pw    Requested param from form, is the users password
     * @param model A generic model, used for passing of information to thymeleaf
     * @return      A String representing html redirect
     */
    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String handleLogin(@RequestParam(name="uname") String name, @RequestParam(name="pw") String pw, Model model){
        pw = hashPass(pw);
        UserEntity user = service.getUser(name);
        System.out.println(user);
        if(user == null){
            model.addAttribute("error",true);
            return "login";
        }
        else if(user.authenticate(pw)){
            model.addAttribute("user",user);
            if(user.getRole().equals("author")){
                return "author";
            }
            else if(user.getRole().equals("admin")){
                return "admin";
            }
            else if (user.getRole().equals("PCM")){
                return "pcm";
            }
            else if (user.getRole().equals("PCC")){
                return "pcc";
            }
            else{
                model.addAttribute("error",true);
                return "login";
            }
        }
        model.addAttribute("error",true);
        return "login";
    }
}

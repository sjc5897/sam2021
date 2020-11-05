package com.sam2021.controller;

import com.sam2021.database.UserEntity;
import com.sam2021.services.AdminService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    AdminService service;

    @RequestMapping(value="/admin", method= RequestMethod.GET)
    public String getAdminPage(@ModelAttribute("user") UserEntity user, Model model){
        if(user != null && user.getRole().equals("admin")){
            model.addAttribute("user",user);
            List<UserEntity> users = service.getUsers();
            model.addAttribute("users",users);
            return "admin";
        }
        else{
            return "redirect:/deny";
        }
    }
}

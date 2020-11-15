package com.sam2021.controller;

import com.sam2021.database.UserEntity;
import com.sam2021.services.AdminService;
import org.apache.catalina.User;
import org.apache.catalina.UserDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    AdminService service;


    @RequestMapping(value="/admin", method= RequestMethod.GET)
    public String getAdminPage(Model model, HttpServletRequest request){

        HttpSession session = request.getSession();
        if(session.isNew()){
            return "redirect:/login";
        }
        Long uid = (Long) session.getAttribute("uid");
        String role = (String) session.getAttribute("role");
        if(!role.equals("admin")){
            return "redirect:/" + role;
        }

        model.addAttribute("users",service.getUsers());
        return "admin";
    }

    @RequestMapping(value="/admin/edit/{id}",method = RequestMethod.GET)
    public String getEditUserPage(@PathVariable("id")Long id, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.isNew()){
            return "redirect:/login";
        }
        String role = (String) session.getAttribute("role");
        if(!role.equals("admin")){
            return "redirect:/" + role;
        }

        model.addAttribute("edit_user",service.getUserById(id));
        return "edit_user";
    }
    @RequestMapping(value="/admin/edit/{id}",method = RequestMethod.POST)
    public String getEditUserPage(@PathVariable("id")Long id, Model model, HttpServletRequest request,
                                  @RequestParam("email")String email, @RequestParam("role")String role,
                                  @RequestParam("f_name")String firstname, @RequestParam("l_name")String l_name,
                                  @RequestParam("submit") String type) {
        HttpSession session = request.getSession();
        if(session.isNew()){
            return "redirect:/login";
        }
        String u_role = (String) session.getAttribute("role");
        if(!u_role.equals("admin")){
            return "redirect:/" + u_role;
        }
        UserEntity user = service.getUserById(id);
        if(type.equals("Save")){
            user.setEmail(email);
            user.setName(firstname,l_name);
            user.setRole(role);
            service.updateUser(user);
        }
        if(type.equals("Delete")){
            service.delete(user);
        }

        return "redirect:/admin/";
    }
    @RequestMapping(value="/admin/delete/{id}", method = RequestMethod.POST)
    public String deleteUser(@PathVariable("id")Long id, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.isNew()){
            return "redirect:/login";
        }
        String u_role = (String) session.getAttribute("role");
        if(!u_role.equals("admin")){
            return "redirect:/" + u_role;
        }

        UserEntity user = service.getUserById(id);
        service.delete(user);
        return "redirect:/admin/";
    }
}

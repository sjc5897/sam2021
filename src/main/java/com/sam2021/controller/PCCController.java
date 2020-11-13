package com.sam2021.controller;

import com.sam2021.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class PCCController {
    @Autowired
    AdminService service;

    @RequestMapping(value="/pcc", method= RequestMethod.GET)
    public String getPCCPage(Model model, HttpServletRequest request){

        HttpSession session = request.getSession();
        if(session.isNew()){
            return "redirect:/login";
        }
        Long uid = (Long) session.getAttribute("uid");
        String role = (String) session.getAttribute("role");
        if(!role.equals("pcc")){
            return "redirect:/" + role;
        }
        return "pcc";
    }
}

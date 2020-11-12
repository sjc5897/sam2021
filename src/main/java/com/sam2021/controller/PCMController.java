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
public class PCMController {
    @Autowired
    AdminService service;

    @RequestMapping(value="/pcm", method= RequestMethod.GET)
    public String getPCMPage(Model model, HttpServletRequest request){

        HttpSession session = request.getSession();
        if(session.isNew()){
            return "redirect:/login";
        }
        String uid = (String) session.getAttribute("uid");
        String role = (String) session.getAttribute("role");
        if(!role.equals("pcm")){
            return "redirect:/" + role;
        }
        return "pcm";
    }

}

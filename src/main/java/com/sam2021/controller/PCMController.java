package com.sam2021.controller;

import com.sam2021.services.AdminService;
import com.sam2021.services.PCMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This is a custom controller for PCM pages into the system
 * Language: Java 13
 * Framework: Spring
 * Author: Stephen Cook <sjc5897@rit.edu>
 * Created: 11/4/20
 * Last Edit: 11/13/20
 */
@Controller
public class PCMController {
    @Autowired
    PCMService service;

    @RequestMapping(value="/pcm", method= RequestMethod.GET)
    public String getPCMPage(Model model, HttpServletRequest request){
        //Autheticate
        HttpSession session = request.getSession();
        if(session.isNew()){
            return "redirect:/login";
        }
        String uid = (String) session.getAttribute("uid");
        String role = (String) session.getAttribute("role");
        if(!role.equals("pcm")){
            return "redirect:/" + role;
        }

        // Get Submitted Papers
        model.addAttribute("s_papers", service.getAllSubmitted());
        // Get Reviews
        model.addAttribute("r_papers", service.getUserReview(uid));
        return "pcm";
    }

    @RequestMapping(value="/pcm/review/{id}", method= RequestMethod.GET)
    public String getPCMReviewPage(@PathVariable("id") Long paper_id,HttpServletRequest request){
        return "";
    }

}

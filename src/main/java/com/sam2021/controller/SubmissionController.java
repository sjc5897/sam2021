package com.sam2021.controller;

import com.sam2021.database.ReviewEntity;
import com.sam2021.database.SubmissionEntity;
import com.sam2021.services.ReviewService;
import com.sam2021.services.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class SubmissionController {
    @Autowired
    SubmissionService service;

    @Autowired
    ReviewService reviewService;


    @RequestMapping(value="/submission/{id}", method= RequestMethod.GET)
    public String getSubmissionDetail(@PathVariable("id") Long id, Model model, HttpServletRequest request){
        SubmissionEntity sub = service.getSubmission(id);

        // Authenticate
        HttpSession session = request.getSession();
        if(session.isNew()){
            return "redirect:/login";
        }
        Long uid = (Long) session.getAttribute("uid");
        String role = (String) session.getAttribute("role");
        if(role.equals("author") && !uid.equals(sub.getAuthor_id())){
            return "redirect:/author";
        }
        else if(role.equals("pcm")){
            boolean ret = reviewService.checkIfPcmAccess(uid,sub.getId());
            if(!ret){
                return "redirect:/pcm";
            }
        }

        if(sub.getCstate().equals("RELEASED")){
            List<ReviewEntity> reviews = reviewService.getReviewsByPaperId(sub.getId());
            model.addAttribute("reviews",reviews);
        }
        model.addAttribute("submission",sub);
        return "sub";
    }
}

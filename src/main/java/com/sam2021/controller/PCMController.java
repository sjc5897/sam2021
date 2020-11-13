package com.sam2021.controller;

import com.sam2021.database.ReviewEntity;
import com.sam2021.database.SubmissionEntity;
import com.sam2021.services.AdminService;
import com.sam2021.services.PCMService;
import org.hibernate.boot.spi.InFlightMetadataCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * This is a custom controller for PCM pages into the system
 * Language: Java 13
 * Framework: Spring
 * Author: Stephen Cook <sjc5897@rit.edu>
 * Created: 11/4/20
 * Last Edit: 11/13/20
 */
@Controller
public class PCMController extends HttpServlet {
    @Autowired
    PCMService service;

    @RequestMapping(value="/pcm", method= RequestMethod.GET)
    public String getPCMPage(Model model, HttpServletRequest request){
        //Autheticate
        Long uid;
        try {
            HttpSession session = request.getSession();
            if(session.isNew()){
                return "redirect:/login";
            }
            uid = (Long) session.getAttribute("uid");
            String role = (String) session.getAttribute("role");
            if(!role.equals("pcm")){
                return "redirect:/" + role;
            }
        }
        catch(Exception ex){
            return "redirect:/login";
        }

        // Get Submitted Papers
        List<SubmissionEntity> submissionEntityList = service.getAllSubmitted();
        if(submissionEntityList != null && submissionEntityList.size() > 0){
            model.addAttribute("s_papers", submissionEntityList);
        }

        List<ReviewEntity> a_reviewEntityList = service.getAllAssignedReviews(uid);
        if(a_reviewEntityList != null && a_reviewEntityList.size() > 0){
            System.out.println(a_reviewEntityList.get(0).getId());
            model.addAttribute("a_reviews", a_reviewEntityList);
        }
        List<ReviewEntity> s_reviewEntityList = service.getAllSubmittedReviews(uid);
        if(s_reviewEntityList != null && s_reviewEntityList.size()>0){
            model.addAttribute("s_reviews", s_reviewEntityList);
        }
        return "pcm";
    }

    @RequestMapping(value="/pcm/review/{id}", method= RequestMethod.GET)
    public String getPCMReviewPage(@PathVariable("id") Long paper_id,HttpServletRequest request){
        return "";
    }

}

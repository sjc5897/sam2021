package com.sam2021.controller;

import com.sam2021.database.ReviewEntity;
import com.sam2021.database.SubmissionEntity;
import com.sam2021.services.AdminService;
import com.sam2021.services.PCMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
        List<ReviewEntity> rr_reviewEntityList = service.getReviewIdandState(uid, "REREVIEW");
        if(rr_reviewEntityList != null && rr_reviewEntityList.size() > 0){
            model.addAttribute("rr_reviews", rr_reviewEntityList);
        }
        List<ReviewEntity> r_reviewEntityList = service.getReviewIdandState(uid, "REQUESTED");
        if(r_reviewEntityList != null && r_reviewEntityList.size() > 0){
            model.addAttribute("r_reviews", r_reviewEntityList);
        }
        List<ReviewEntity> a_reviewEntityList = service.getReviewIdandState(uid, "ASSIGNED");
        if(a_reviewEntityList != null && a_reviewEntityList.size() > 0){
            model.addAttribute("a_reviews", a_reviewEntityList);
        }
        List<ReviewEntity> s_reviewEntityList = service.getReviewIdandState(uid, "SUBMITTED");
        if(s_reviewEntityList != null && s_reviewEntityList.size()>0){
            model.addAttribute("s_reviews", s_reviewEntityList);
        }
        return "pcm";
    }

    @RequestMapping(value="/pcm/request/{id}", method = RequestMethod.GET)
    public String getPCMRequestForm(@PathVariable("id") Long paper_id, HttpServletRequest request, Model model){
        Long uid;
        String role;
        try {
            HttpSession session = request.getSession();
            if(session.isNew()){
                return "redirect:/login";
            }
            uid = (Long) session.getAttribute("uid");
            role = (String) session.getAttribute("role");
            if(!role.equals("pcm")){
                return "redirect:/" + role;
            }
        }
        catch(Exception ex){
            return "redirect:/login";
        }
        // vfy paper
        SubmissionEntity paper = service.getSubmissionById(paper_id);
        if(paper == null){
            model.addAttribute("error", "Paper does not exist");
            return "redirect:/" + role;
        }
        if(!paper.getCstate().equals("SUBMITTED")){
            model.addAttribute("error", "Paper is already assigned");
            return "redirect:/" + role;
        }
        if(!service.isRequestedAlready(paper_id,uid)){
            model.addAttribute("error", "Paper is already requested ");
            return "redirect:/" + role;
        }
        if(!service.addNewReview(paper_id,uid)){
            model.addAttribute("error","Unknown error: try again");
        }
        return "redirect:/" + role;
    }

    @RequestMapping(value="/pcm/review/{id}", method= RequestMethod.GET)
    public String getPCMReviewPage(@PathVariable("id") Long review_id, Model model, HttpServletRequest request){
        //Authenticate
        Long uid;
        String role;
        try {
            HttpSession session = request.getSession();
            if(session.isNew()){
                return "redirect:/login";
            }
            uid = (Long) session.getAttribute("uid");
            role = (String) session.getAttribute("role");
            if(!role.equals("pcm")){
                return "redirect:/" + role;
            }
        }
        catch(Exception ex){
            return "redirect:/login";
        }
        ReviewEntity reviewEntity = service.getReviewById(review_id);
        if(reviewEntity == null || reviewEntity.getReviewer_id() != uid || reviewEntity.getCstate().equals("REQUESTED")){
            model.addAttribute("error", "Invalid Review Request");
            return "redirect:/" + role;
        }
        SubmissionEntity submissionEntity = service.getSubmissionById(reviewEntity.getPaper_id());

        model.addAttribute("submission", submissionEntity);
        model.addAttribute("review",reviewEntity);

        return "sub";
    }
    @RequestMapping(value="/pcm/review/{id}", method= RequestMethod.POST)
    public String submitReviewPage(@PathVariable("id") Long review_id, @RequestParam("rating") int rating,
                                   @RequestParam("comments") String comments, @RequestParam("action") String action,
                                   Model model, HttpServletRequest request){
        //Authenticate
        Long uid;
        String role;
        try {
            HttpSession session = request.getSession();
            if(session.isNew()){
                return "redirect:/login";
            }
            uid = (Long) session.getAttribute("uid");
            role = (String) session.getAttribute("role");
            if(!role.equals("pcm")){
                return "redirect:/" + role;
            }
        }
        catch(Exception ex){
            return "redirect:/login";
        }
        ReviewEntity reviewEntity = service.getReviewById(review_id);
        if(reviewEntity == null || reviewEntity.getReviewer_id() != uid || reviewEntity.getCstate().equals("REQUESTED")){
            model.addAttribute("error", "Invalid Review Request");
            return "redirect:/" + role;
        }
        if(action.equals("submit")){
            reviewEntity.setCstate("SUBMITTED");
        }
        reviewEntity.setRating(rating);
        reviewEntity.setComments(comments);
        if(service.update(reviewEntity)){
            model.addAttribute("success",true);
            return "redirect:/pcm/review/" + reviewEntity.getId();
        }else{
            model.addAttribute("success",false);
            return "redirect:/pcm/review/" + reviewEntity.getId();
        }
    }

}

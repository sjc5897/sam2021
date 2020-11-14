package com.sam2021.controller;

import com.sam2021.database.ReviewEntity;
import com.sam2021.database.SubmissionEntity;
import com.sam2021.database.UserEntity;
import com.sam2021.services.AdminService;
import com.sam2021.services.PCCService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
public class PCCController {
    @Autowired
    PCCService service;

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

        //get reports
        List<SubmissionEntity> submissionEntityList = service.getSubmissionbyState("SUBMITTED");
        if(submissionEntityList != null && submissionEntityList.size() > 0){
            model.addAttribute("s_papers", submissionEntityList);
        }
        List<SubmissionEntity> inProgressList = service.getSubmissionbyState("REVIEWING");
        if(inProgressList != null && inProgressList.size() > 0){
            model.addAttribute("i_papers", inProgressList);
        }
        List<SubmissionEntity> reviewedPapersList = service.getSubmissionbyState("REVIEWED");
        if(reviewedPapersList != null && reviewedPapersList.size() > 0){
            model.addAttribute("r_papers", reviewedPapersList);
        }
        return "pcc";
    }
    @RequestMapping(value="/pcc/review/{id}", method=RequestMethod.GET)
    public String getPCCReviewPage(@PathVariable("id") Long paper_id, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.isNew()){
            return "redirect:/login";
        }
        Long uid = (Long) session.getAttribute("uid");
        String role = (String) session.getAttribute("role");
        if(!role.equals("pcc")){
            return "redirect:/" + role;
        }

        //Get Paper
        SubmissionEntity submissionEntity = service.getSubmissionById(paper_id);
        if(submissionEntity==null){
            model.addAttribute("error","Submission not Found");
            return "redirect:/pcc";
        }
        model.addAttribute("submission", submissionEntity);
        if(submissionEntity.getCstate().equals("SUBMITTED")){
            List<ReviewEntity> reviewRequests = service.getReviewByPaperIdAndState(paper_id,"REQUESTED");
            if(reviewRequests != null && reviewRequests.size() > 1){
                model.addAttribute("req_reviews", reviewRequests);
                HashMap<Long, UserEntity> reviewer = null;
                for(ReviewEntity r : reviewRequests){
                    reviewer.put(r.getReviewer_id(), service.getReviewer(r.getReviewer_id()));
                }
            }
        }
        else{
            List<ReviewEntity> reviews = service.getReviewByPaperId(paper_id);
            if(reviews != null && reviews.size() > 1){
                model.addAttribute("ack_reviews", reviews);
                HashMap<Long, UserEntity> reviewer = null;
                for(ReviewEntity r : reviews){
                    reviewer.put(r.getReviewer_id(), service.getReviewer(r.getReviewer_id()));
                }

            }
        }
        return "sub";
    }

    @RequestMapping(value="/pcc/assign/{id}", method = RequestMethod.GET)
    public String approveReviewReq(@PathVariable("id") Long review_id, HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        if(session.isNew()){
            return "redirect:/login";
        }
        Long uid = (Long) session.getAttribute("uid");
        String role = (String) session.getAttribute("role");
        if(!role.equals("pcc")){
            return "redirect:/" + role;
        }

        ReviewEntity reviewEntity = service.getReviewByReviewId(review_id);
        if(reviewEntity==null){
            model.addAttribute("error","Review not Found");
            return "redirect:/pcc";
        }

        SubmissionEntity submissionEntity = service.getSubmissionById(reviewEntity.getPaper_id());
        if(!submissionEntity.getCstate().equals("SUBMITTED")){
            model.addAttribute("error","Submission has 3 reviewers assigned");
            return "redirect:/pcc";
        }
        service.assignReview(reviewEntity,submissionEntity);

        return "redirect:/pcc/review/" + submissionEntity.getId();



    }
    @RequestMapping(value="/pcc/rereview/{id}", method = RequestMethod.GET)
    public String rereview(@PathVariable("id") Long paper_id, HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        if(session.isNew()){
            return "redirect:/login";
        }
        Long uid = (Long) session.getAttribute("uid");
        String role = (String) session.getAttribute("role");
        if(!role.equals("pcc")){
            return "redirect:/" + role;
        }

        List<ReviewEntity> reviewEntities = service.getReviewsByPaperId(paper_id);
        if(reviewEntities==null || reviewEntities.size() < 3){
            model.addAttribute("error","Review not Found");
            return "redirect:/pcc";
        }

        service.rereview(reviewEntities);

        return "redirect:/pcc/review/" + reviewEntities.get(0).getPaper_id();
    }

    @RequestMapping(value="/pcc/report/{id}", method = RequestMethod.GET)
    public String getReportForm(@PathVariable("id") Long id, HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        if(session.isNew()){
            return "redirect:/login";
        }
        Long uid = (Long) session.getAttribute("uid");
        String role = (String) session.getAttribute("role");
        if(!role.equals("pcc")){
            return "redirect:/" + role;
        }

        List<ReviewEntity> reviewEntities = service.getReviewByPaperId(id);
        if(reviewEntities==null || reviewEntities.size() < 3){
            model.addAttribute("error","Report for paper unavailable");
            return "redirect:/pcc";
        }
        model.addAttribute("reviews",reviewEntities);
        int ave = 0;
        for(ReviewEntity e : reviewEntities){
            ave += e.getRating();
        }
        ave = ave/3;
        model.addAttribute("sub_id", reviewEntities.get(0).getPaper_id());
        model.addAttribute("ave",ave);
        return "report_create";
    }

    @RequestMapping(value="/pcc/report/{id}", method = RequestMethod.POST)
    public String getReportForm(@PathVariable("id") Long id,
                                @RequestParam("rating") int rating,
                                @RequestParam("comments") String PCCcmt,
                                HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        if(session.isNew()){
            return "redirect:/login";
        }
        Long uid = (Long) session.getAttribute("uid");
        String role = (String) session.getAttribute("role");
        if(!role.equals("pcc")){
            return "redirect:/" + role;
        }
        service.submitReport(uid, id, rating, PCCcmt);

        return "redirect:/pcc/review/{id}" + id;
    }
}

package com.sam2021.controller;

import com.sam2021.database.ReviewEntity;
import com.sam2021.database.SubmissionEntity;
import com.sam2021.database.UserEntity;
import com.sam2021.services.ReviewService;
import com.sam2021.services.UserService;
import com.sam2021.services.SubmissionService;
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

/**
 * This is a custom controller for PCC pages into the system
 * Language: Java 13
 * Framework: Spring
 * Author: Stephen Cook <sjc5897@rit.edu>
 * Created: 11/4/20
 * Last Edit: 11/14/20
 */
@Controller
public class PCCController {
    //Services
    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;
    /**
     * gets the PCC homepage
     * @param model     Model for frontend
     * @param request   HttpRequest Servlet
     * @return          String representing redirect
     */
    @RequestMapping(value="/pcc", method= RequestMethod.GET)
    public String getPCCPage(Model model, HttpServletRequest request){

        // Authenticate request
        HttpSession session = request.getSession();
        String ret = userService.auth(session,"pcc");
        if(ret != null){
            return ret;
        }

        //get submitted papers
        List<SubmissionEntity> submissionEntityList = submissionService.getSubmissionbyState("SUBMITTED");
        if(submissionEntityList != null && submissionEntityList.size() > 0){
            model.addAttribute("s_papers", submissionEntityList);
        }

        //get papers being reviewed
        List<SubmissionEntity> inProgressList = submissionService.getSubmissionbyState("REVIEWING");
        if(inProgressList != null && inProgressList.size() > 0){
            model.addAttribute("i_papers", inProgressList);
        }

        //get papers that have been reviewed
        List<SubmissionEntity> reviewedPapersList = submissionService.getSubmissionbyState("REVIEWED");
        if(reviewedPapersList != null && reviewedPapersList.size() > 0){
            model.addAttribute("r_papers", reviewedPapersList);
        }
        return "pcc";
    }

    /**
     * Gets the pcc version of the review page
     * @param paper_id  Long the requested paper if
     * @param model     Model the model of objects
     * @param request   HttpServletRequest the request
     * @return          String representing redirect
     */
    @RequestMapping(value="/pcc/review/{id}", method=RequestMethod.GET)
    public String getPCCReviewPage(@PathVariable("id") Long paper_id, Model model, HttpServletRequest request){
        // Authenticate request
        HttpSession session = request.getSession();
        String ret = userService.auth(session,"pcc");
        if(ret != null){
            return ret;
        }

        //Get Paper
        SubmissionEntity submissionEntity = submissionService.getSubmission(paper_id);
        if(submissionEntity==null){
            model.addAttribute("error","Submission not Found");
            return "redirect:/pcc";
        }
        model.addAttribute("submission", submissionEntity);

        // If paper is in submitted get all the requested reviews
        if(submissionEntity.getCstate().equals("SUBMITTED")){
            List<ReviewEntity> reviewRequests = reviewService.getReviewByPaperIdAndState(paper_id,"REQUESTED");
            if(reviewRequests != null && reviewRequests.size() > 0){
                model.addAttribute("req_reviews", reviewRequests);
                HashMap<Long, UserEntity> reviewer = new HashMap<>();
                for(ReviewEntity r : reviewRequests){
                    reviewer.put(r.getReviewer_id(), userService.getUserById(r.getReviewer_id()));
                }
                model.addAttribute("reviewer",reviewer);
            }
        }

        // Otherwise get all the regular reviews
        else{
            List<ReviewEntity> reviews = reviewService.getReviewsByPaperId(paper_id);
            if(reviews != null && reviews.size() > 0){
                model.addAttribute("ack_reviews", reviews);
                HashMap<Long, UserEntity> reviewer = new HashMap<>();
                for(ReviewEntity r : reviews){
                    reviewer.put(r.getReviewer_id(), userService.getUserById(r.getReviewer_id()));
                }
                model.addAttribute("reviewer",reviewer);
            }
        }
        return "sub";
    }

    /**
     * Approves the request for review from a PCM
     * @param review_id The id of the review being approved
     * @param request   HttpServletRequest the request
     * @param model     Model the model of objects
     * @return          String representing redirect
     */
    @RequestMapping(value="/pcc/assign/{id}", method = RequestMethod.GET)
    public String approveReviewReq(@PathVariable("id") Long review_id, HttpServletRequest request, Model model){
        // Authenticate request
        HttpSession session = request.getSession();
        String ret = userService.auth(session,"pcc");
        if(ret != null){
            return ret;
        }

        // Gets review
        ReviewEntity reviewEntity = reviewService.getReviewByReviewId(review_id);
        if(reviewEntity==null){
            model.addAttribute("error","Review not Found");
            return "redirect:/pcc";
        }

        // checks that submission is valid
        SubmissionEntity submissionEntity = submissionService.getSubmission(reviewEntity.getPaper_id());
        if(!submissionEntity.getCstate().equals("SUBMITTED")){
            model.addAttribute("error","Submission has 3 reviewers assigned");
            return "redirect:/pcc";
        }

        //does submission
        if(reviewService.assignReview(reviewEntity)){
            submissionService.updateState("REVIEWING", submissionEntity.getId());
        }

        return "redirect:/pcc/review/" + submissionEntity.getId();

    }

    /**
     * Handles the request for re-review
     * @param paper_id  Long id of paper to be rereviewed
     * @param request   HttpServletRequest the request
     * @param model     Model the model of objects
     * @return          String representing redirect
     */
    @RequestMapping(value="/pcc/rereview/{id}", method = RequestMethod.GET)
    public String rereview(@PathVariable("id") Long paper_id, HttpServletRequest request, Model model){
        // Authenticate request
        HttpSession session = request.getSession();
        String ret = userService.auth(session,"pcc");
        if(ret != null){
            return ret;
        }

        // Gets list of reviews
        List<ReviewEntity> reviewEntities = reviewService.getReviewsByPaperId(paper_id);
        if(reviewEntities==null || reviewEntities.size() < 3){
            model.addAttribute("error","Review not Found");
            return "redirect:/pcc";
        }

        // Submits rereview
        reviewService.rereview(reviewEntities);
        submissionService.updateState("REVIEWING", reviewEntities.get(0).getPaper_id());

        return "redirect:/pcc/review/" + paper_id;
    }

    /**
     * Gets the paper report creation form
     * @param id        Long id of paper report is being generated for
     * @param request   HttpServletRequest the request
     * @param model     Model the model of objects
     * @return          String representing redirect
     */
    @RequestMapping(value="/pcc/report/{id}", method = RequestMethod.GET)
    public String getReportForm(@PathVariable("id") Long id, HttpServletRequest request, Model model){
        // Authenticate request
        HttpSession session = request.getSession();
        String ret = userService.auth(session,"pcc");
        if(ret != null){
            return ret;
        }

        // Gets the paper's reviews
        List<ReviewEntity> reviewEntities = reviewService.getReviewsByPaperId(id);
        if(reviewEntities==null || reviewEntities.size() < 3){
            model.addAttribute("error","Report for paper unavailable");
            return "redirect:/pcc";
        }
        model.addAttribute("reviews",reviewEntities);

        HashMap<Long, UserEntity> reviewer = new HashMap<>();
        for(ReviewEntity r : reviewEntities){
            reviewer.put(r.getReviewer_id(), userService.getUserById(r.getReviewer_id()));
        }
        model.addAttribute("reviewer",reviewer);

        // Calcs average
        int ave = 0;
        for(ReviewEntity e : reviewEntities){
            ave += e.getRating();
        }
        ave = ave/3;


        model.addAttribute("sub_id", id);
        model.addAttribute("ave",ave);
        return "report_create";
    }

     /**
     * Handles submission of paper report form
     * @param id        Long id of paper report is being generated for
     * @param rating    int PCC's rating
     * @param PCCcmt    String PCC's Comments
     * @param request   HttpServletRequest the request
     * @param model     Model the model of objects
     * @return          String representing redirect
     */
    @RequestMapping(value="/pcc/report/{id}", method = RequestMethod.POST)
    public String postReportForm(@PathVariable("id") Long id,
                                @RequestParam("rating") int rating,
                                @RequestParam("comments") String PCCcmt,
                                HttpServletRequest request, Model model){
        // Authenticate request
        HttpSession session = request.getSession();
        String ret = userService.auth(session,"pcc");
        if(ret != null){
            return ret;
        }

        //Submits report
        reviewService.submitReport((Long) session.getAttribute("uid"), id, rating, PCCcmt);
        submissionService.updateState("RELEASED", id);

        return "redirect:/pcc/";
    }
}

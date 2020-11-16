package com.sam2021.controller;

import com.sam2021.database.ReviewEntity;
import com.sam2021.database.SubmissionEntity;
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
 * Last Edit: 11/15/20
 */
@Controller
public class PCMController extends HttpServlet {
    //Services
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;
    @Autowired
    private SubmissionService submissionService;

    /**
     * Handles the get of the PCM page
     * @param model     Model the model of objects
     * @param request   HttpServletRequest the request
     * @return          String representing redirect
     */
    @RequestMapping(value="/pcm", method= RequestMethod.GET)
    public String getPCMPage(Model model, HttpServletRequest request){
        //Authenticate
        HttpSession session = request.getSession();
        String ret = userService.auth(session,"pcm");
        if(ret != null){
            return ret;
        }
        Long uid = (Long) session.getAttribute("uid");

        // Get Submitted Papers
        List<SubmissionEntity> submissionEntityList = submissionService.getSubmissionbyState("SUBMITTED");
        if(submissionEntityList != null && submissionEntityList.size() > 0){
            model.addAttribute("s_papers", submissionEntityList);
        }
        // Get all re-requested reviews for user
        List<ReviewEntity> rr_reviewEntityList = reviewService.getReviewerIdandState(uid, "REREVIEW");
        if(rr_reviewEntityList != null && rr_reviewEntityList.size() > 0){
            model.addAttribute("rr_reviews", rr_reviewEntityList);
        }
        // Get all requested reviews for user
        List<ReviewEntity> r_reviewEntityList = reviewService.getReviewerIdandState(uid, "REQUESTED");
        if(r_reviewEntityList != null && r_reviewEntityList.size() > 0){
            model.addAttribute("r_reviews", r_reviewEntityList);
        }
        // Get all assigned reviews for user
        List<ReviewEntity> a_reviewEntityList = reviewService.getReviewerIdandState(uid, "ASSIGNED");
        if(a_reviewEntityList != null && a_reviewEntityList.size() > 0){
            model.addAttribute("a_reviews", a_reviewEntityList);
        }
        // Get all submitted reviews for user
        List<ReviewEntity> s_reviewEntityList = reviewService.getReviewerIdandState(uid, "SUBMITTED");
        if(s_reviewEntityList != null && s_reviewEntityList.size()>0){
            model.addAttribute("s_reviews", s_reviewEntityList);
        }
        return "pcm";
    }

    /**
     * Gets the pcm request page
     * @param paper_id  Long representing paper ID
     * @param model     Model the model of objects
     * @param request   HttpServletRequest the request
     * @return          String representing redirect
     */
    @RequestMapping(value="/pcm/request/{id}", method = RequestMethod.GET)
    public String getPCMRequestForm(@PathVariable("id") Long paper_id, HttpServletRequest request, Model model){
        //Authenticate
        HttpSession session = request.getSession();
        String ret = userService.auth(session,"pcm");
        if(ret != null){
            return ret;
        }
        Long uid = (Long) session.getAttribute("uid");


        // vfy paper exists
        SubmissionEntity paper = submissionService.getSubmission(paper_id);
        if(paper == null){
            model.addAttribute("error", "Paper does not exist");
        }
        // check it is in submitted state
        else if(!paper.getCstate().equals("SUBMITTED")){
            model.addAttribute("error", "Paper is already assigned");
        }
        // check if request already exists

        // error in review creation
        else if(!reviewService.addNewReview(paper_id,uid)){
            model.addAttribute("error", "Paper is already requested ");
        }
        return "redirect:/pcm";
    }

    /**
     * Gets the review page
     * @param review_id Review Id
     * @param model     Model the model of objects
     * @param request   HttpServletRequest the request
     * @return          String representing redirect
     */
    @RequestMapping(value="/pcm/review/{id}", method= RequestMethod.GET)
    public String getPCMReviewPage(@PathVariable("id") Long review_id, Model model, HttpServletRequest request){
        //Authenticate
        HttpSession session = request.getSession();
        String ret = userService.auth(session,"pcm");
        if(ret != null){
            return ret;
        }
        Long uid = (Long) session.getAttribute("uid");


        ReviewEntity reviewEntity = reviewService.getReviewByReviewId(review_id);
        // ensure the submission exists
        if(reviewEntity == null || reviewEntity.getReviewer_id() != uid || reviewEntity.getCstate().equals("REQUESTED")){
            model.addAttribute("error", "Invalid Review Request");
            return "redirect:/pcm";
        }
        // Get submission
        SubmissionEntity submissionEntity = submissionService.getSubmission(reviewEntity.getPaper_id());

        model.addAttribute("submission", submissionEntity);
        model.addAttribute("review",reviewEntity);

        return "sub";
    }

    /**
     * Submits a review
     * @param review_id Long representing the review id
     * @param rating    int representing the rating
     * @param comments  String repenting comments
     * @param action    String representing save or submit
     * @param model     Model the model of objects
     * @param request   HttpServletRequest the request
     * @return          String representing redirect
     */
    @RequestMapping(value="/pcm/review/{id}", method= RequestMethod.POST)
    public String submitReviewPage(@PathVariable("id") Long review_id, @RequestParam("rating") int rating,
                                   @RequestParam("comments") String comments, @RequestParam("action") String action,
                                   Model model, HttpServletRequest request){
        //Authenticate
        //Authenticate
        HttpSession session = request.getSession();
        String ret = userService.auth(session,"pcm");
        if(ret != null){
            return ret;
        }
        Long uid = (Long) session.getAttribute("uid");


        ReviewEntity reviewEntity = reviewService.getReviewByReviewId(review_id);

        // Invalid review
        if(reviewEntity == null || reviewEntity.getReviewer_id() != uid || reviewEntity.getCstate().equals("REQUESTED")){
            model.addAttribute("error", "Invalid Review Request");
            return "redirect:/pcm";
        }

        // if submit
        if(action.equals("submit")){
            reviewEntity.setCstate("SUBMITTED");
        }

        // update the values
        reviewEntity.setRating(rating);
        reviewEntity.setComments(comments);

        // update
        if(reviewService.update(reviewEntity)){
            submissionService.updateState("REVIEWED", reviewEntity.getPaper_id());
            model.addAttribute("success",true);

        }else{
            model.addAttribute("success",false);
        }
        return "redirect:/pcm/review/" + reviewEntity.getId();
    }

}

package com.sam2021.controller;

import com.sam2021.database.SubmissionEntity;
import com.sam2021.services.AuthenitcationService;
import com.sam2021.services.FileService;
import com.sam2021.services.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * This is a custom controller for Author pages into the system
 * Language: Java 13
 * Framework: Spring
 * Author: Stephen Cook <sjc5897@rit.edu>, Malcolm Lambrecht
 * Created: 10/26/20
 * Last Edit: 11/15/20
 */
@Controller
public class AuthorController {
    // Services
    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private FileService fileService;

    @Autowired
    private AuthenitcationService authenitcationService;

    /**
     * This handles gets to the url /author
     * @param model     The object model for thymeleaf
     * @param request   The http servlet request
     * @return          String representing either redirect or author page
     */
    @RequestMapping(value="/author", method= RequestMethod.GET)
    public String getAuthorPage(Model model, HttpServletRequest request){

        // Authenticate request
        HttpSession session = request.getSession();
        String ret = authenitcationService.auth(session,"author");
        if(ret != null){
            return ret;
        }

        // Get submissions
        List<SubmissionEntity> submissions = submissionService.getAuthorsSubmissions((Long) session.getAttribute("uid"));
        if(submissions != null &&  submissions.size() > 1){
            model.addAttribute("submissions", submissions);
        }

        // Return page
        return "author";
    }

    /**
     * Handles get to url /author/new
     * @param request   HttpServletRequest of the current request
     * @return String representing the new submission form
     */
    @RequestMapping(value="/author/new", method = RequestMethod.GET)
    public String getSubmissionPage(HttpServletRequest request){
        // Authenticate request
        HttpSession session = request.getSession();
        String ret = authenitcationService.auth(session,"author");
        if(ret != null){
            return ret;
        }
        return "new_sub";
    }

    /**
     * Handles Posts from the new submission form
     * @param request   HttpServletRequest of the current request
     * @param file      Multipart File representing file path
     * @param email     String representing contact email address
     * @param title     String representing title
     * @param format    String representing the file format
     * @param authorList String representing the authors
     * @param version   String representing the version of the paper
     * @return String representing redirect
     */
    @RequestMapping(value="/author/new", method = RequestMethod.POST)
    public String submit(HttpServletRequest request,
                         @RequestParam("file") MultipartFile file,
                         @RequestParam("email")String email,
                         @RequestParam("title")String title,
                         @RequestParam("format") String format,
                         @RequestParam("authorList") String authorList,
                         @RequestParam(value = "version", required = false) String version){

        // Authenticate request
        HttpSession session = request.getSession();
        String ret = authenitcationService.auth(session,"author");
        if(ret != null){
            return ret;
        }

        //Create new submission
        boolean suc = submissionService.addNewSubmission(email,title,file.getOriginalFilename(),
                format,authorList,Integer.parseInt(version),
                (Long) session.getAttribute("uid"));
        // Upload file
        if(suc){
            fileService.uploadFile(file);
        }

        //Upload the file

        return "redirect:/author";
    }

}

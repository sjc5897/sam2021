package com.sam2021.controller;

import com.sam2021.database.SubmissionEntity;
import com.sam2021.database.UserEntity;
import com.sam2021.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AuthorController extends HttpServlet {
    @Autowired
    AuthorService service;

    @RequestMapping(value="/author", method= RequestMethod.GET)
    public String getAuthorPage(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.isNew()){
            return "redirect:/login";
        }
        String uid = (String) session.getAttribute("uid");
        String role = (String) session.getAttribute("role");

        UserEntity user = service.getAuthor(uid);
        List<SubmissionEntity> submissions = service.getAuthorsSubmissions(user.getEmail());
        if(submissions.size() != 0){
            model.addAttribute("submissions", submissions);
        }
        model.addAttribute("user",user);

        return "author";
    }

    @RequestMapping(value="/author",method= RequestMethod.POST)
    public String handleAuthorSubmit(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.isNew()){
            return "redirect:/login";
        }
        String uid = (String) session.getAttribute("uid");

        model.addAttribute("user", service.getAuthor(uid));
        return "redirect:/author/new";
    }

    @RequestMapping(value="/author/new", method = RequestMethod.GET)
    public String getSubmissionPage(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.isNew()){
            return "redirect:/login";
        }
        String uid = (String) session.getAttribute("uid");

        model.addAttribute("user", service.getAuthor(uid));
        return "new_sub";
    }

    @RequestMapping(value="/author/new", method = RequestMethod.POST)
    public String submit(RedirectAttributes redirectAttributes,
                         @RequestParam("file") MultipartFile file,
                         @RequestParam("email")String email,
                         @RequestParam("title")String title,
                         @RequestParam("format") String format,
                         @RequestParam("authorList") String authorList,
                         @RequestParam(value = "version", required = false) String version,
                         Model model, HttpServletRequest request){

        HttpSession session = request.getSession();
        if(session.isNew()){
            return "redirect:/login";
        }

        //contact email must be a registered email
        //if revision, title must exist
        if(version != null){
            redirectAttributes.addFlashAttribute("message", "Error: paper title must already exist to");
            return "redirect:/author/new";
        }

        //service.addNewSubmission(email,title,file.getOriginalFilename(),"."+format,authorList,0,0);
        service.uploadFile(file);
        return "author";
    }

}

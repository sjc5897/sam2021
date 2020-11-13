package com.sam2021.controller;

import com.sam2021.database.SubmissionEntity;
import com.sam2021.database.UserEntity;
import com.sam2021.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AuthorController {
    @Autowired
    AuthorService service;

    @RequestMapping(value="/author", method= RequestMethod.GET)
    public String getAuthorPage(@ModelAttribute("user") UserEntity user, Model model, HttpServletRequest request){

        HttpSession session = request.getSession();
        if(session.isNew()){
            return "redirect:/login";
        }
        String uid = (String) session.getAttribute("uid");
        String role = (String) session.getAttribute("role");
        if(!role.equals("author")){
            return "redirect:/" + role;
        }

        user = service.getAuthor((String)session.getAttribute("uid"));
        List<SubmissionEntity> submissions = service.getAuthorsSubmissions(user.getEmail());
        if(submissions.size() != 0){
            model.addAttribute("submissions", submissions);
        }
        model.addAttribute("user",user);
        model.addAttribute("name",user.getName());
        return "author";
    }

    @RequestMapping(value="/author",method= RequestMethod.POST)
    public String handleAuthorSubmit(@ModelAttribute("user") UserEntity user, Model model){
        model.addAttribute("user", user);
        return "redirect:/author";
    }

    @RequestMapping(value="/author/new", method = RequestMethod.GET)
    public String getSubmissionPage(@ModelAttribute("user") UserEntity user,Model model){
        model.addAttribute("user", user);
        return "new_sub";
    }

    @RequestMapping(value="/author/new", method = RequestMethod.POST)
    public String submit(RedirectAttributes redirectAttributes,
                         HttpServletRequest request,
                         @RequestParam("file") MultipartFile file,
                         @RequestParam("email")String email,
                         @RequestParam("title")String title,
                         @RequestParam("format") String format,
                         @RequestParam("authorList") String authorList,
                         @RequestParam(value = "version", required = false) String version,
                         @ModelAttribute("user") UserEntity user,
                         Model model){

        HttpSession session = request.getSession();
        if(session.isNew()){
            return "redirect:/login";
        }
        String uid = (String) session.getAttribute("uid");
        String role = (String) session.getAttribute("role");
        if(!role.equals("author")){
            return "redirect:/" + role;
        }

        user = service.getAuthor((String)session.getAttribute("uid"));
        model.addAttribute("user",user);

        service.addNewSubmission(email,title,file.getOriginalFilename(),format,authorList,Integer.parseInt(version),user.getid().intValue());
        service.uploadFile(file);
        return "redirect:/author";
    }

}

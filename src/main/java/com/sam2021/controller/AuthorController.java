package com.sam2021.controller;

import com.sam2021.database.SubmissionEntity;
import com.sam2021.database.UserEntity;
import com.sam2021.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class AuthorController {
    @Autowired
    AuthorService service;

    @RequestMapping(value="/author", method= RequestMethod.GET)
    public String getAuthorPage(@ModelAttribute("user") UserEntity user, Model model){

        List<SubmissionEntity> submissions = service.getAuthorsSubmissions(user.getEmail());
        model.addAttribute("submissions", submissions);
        return "author";
    }

    @RequestMapping(value="/author",method= RequestMethod.POST)
    public String handleAuthorSubmit(Model model){
        return "redirect:/author/new";
    }

    @RequestMapping(value="/author/new", method = RequestMethod.GET)
    public String getSubmissionPage(Model model){
        return "new_sub";
    }

    @RequestMapping(value="/author/new", method = RequestMethod.POST)
    public String submit(){
        return "author";
    }

}

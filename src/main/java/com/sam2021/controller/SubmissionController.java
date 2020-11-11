package com.sam2021.controller;

import com.sam2021.database.SubmissionEntity;
import com.sam2021.services.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@Controller
public class SubmissionController {
    @Autowired
    SubmissionService service;

    @RequestMapping(value="/submission/{id}", method= RequestMethod.GET)
    public String getSubmissionDetail(@PathVariable("id") Long id, Model model){
        System.out.println(id);
        List<SubmissionEntity> sub = service.getSubmission(id);
        System.out.println(sub.get(0));
        System.out.println(sub.get(0).getEmail());
        model.addAttribute("submission",sub.get(0));
        return "sub";
    }
}
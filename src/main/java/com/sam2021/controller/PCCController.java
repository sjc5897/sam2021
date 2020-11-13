package com.sam2021.controller;

import com.sam2021.database.SubmissionEntity;
import com.sam2021.services.AdminService;
import com.sam2021.services.PCCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
}

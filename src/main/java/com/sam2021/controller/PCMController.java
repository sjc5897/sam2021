package com.sam2021.controller;

import com.sam2021.services.PCMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PCMController {
    @Autowired
    PCMService service;

    @RequestMapping(value = "/pcm", method = RequestMethod.GET)
    public String getPCMHome(){

    }
}

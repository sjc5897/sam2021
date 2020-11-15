package com.sam2021.controller;

import com.sam2021.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
public class FileController {

    @Autowired
    FileService fileService;

    @Autowired
    ServletContext context;

    @RequestMapping(value="/download", method= RequestMethod.GET)
    public String getDownloadFile(Model model, @RequestParam("fileName")String fileName, HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        if(session.isNew()){
            return "redirect:/login";
        }

        try{
            File file = new File(context.getRealPath("/") +"uploadDir"+ File.separator + fileName);
            FileInputStream in = new FileInputStream(file);
            byte[] content = new byte[(int) file.length()];
            in.read(content);
            String mimetype = context.getMimeType(file.getName());
            response.reset();
            response.setContentType(mimetype);
            response.setContentLength(content.length);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
            org.springframework.util.FileCopyUtils.copy(content, response.getOutputStream());
        } catch( IOException e){
            System.out.println(e.getMessage());
        }
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }
}

package com.sam2021.controller;

import com.sam2021.database.UserEntity;
import com.sam2021.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This is a custom controller for admins
 * Language: Java 13
 * Framework: Spring
 * Author: Stephen Cook <sjc5897@rit.edu>
 * Created: 10/24/20
 * Last Edit: 11/15/20
 */
@Controller
public class AdminController {
    // Services
    @Autowired
    private UserService userService;

    /**
     * Gets the admin homepage
     *
     * @param request HttpServletRequest the request
     * @param model   Model the model of objects
     * @return String representing redirect
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String getAdminPage(Model model, HttpServletRequest request) {
        // Auth
        HttpSession session = request.getSession();
        String ret = userService.auth(session, "admin");
        if (ret != null) {
            return ret;
        }

        //Get all users
        model.addAttribute("users", userService.getUsers());
        return "admin";
    }

    /**
     * Gets edit page for a user
     *
     * @param id      User to edit
     * @param request HttpServletRequest the request
     * @param model   Model the model of objects
     * @return String representing redirect
     */
    @RequestMapping(value = "/admin/edit/{id}", method = RequestMethod.GET)
    public String getEditUserPage(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        // Auth
        HttpSession session = request.getSession();
        String ret = userService.auth(session, "admin");
        if (ret != null) {
            return ret;
        }

        model.addAttribute("edit_user", userService.getUserById(id));
        return "edit_user";
    }

    /**
     * Submits edit
     *
     * @param id        Id of user to edit
     * @param request   HttpServletRequest the request
     * @param model     Model the model of objects
     * @param email     new email
     * @param role      new role
     * @param firstname new firstname
     * @param l_name    new last name
     * @param type      type of submission
     * @return String representing redirect
     */
    @RequestMapping(value = "/admin/edit/{id}", method = RequestMethod.POST)
    public String getEditUserPage(@PathVariable("id") Long id, Model model, HttpServletRequest request,
                                  @RequestParam("email") String email, @RequestParam("role") String role,
                                  @RequestParam("f_name") String firstname, @RequestParam("l_name") String l_name,
                                  @RequestParam("submit") String type) {
        // Auth
        HttpSession session = request.getSession();
        String ret = userService.auth(session, "admin");
        if (ret != null) {
            return ret;
        }

        // get user
        UserEntity user = userService.getUserById(id);

        // if action save
        if (type.equals("Save")) {
            user.setEmail(email);
            user.setName(firstname, l_name);
            user.setRole(role);
            userService.updateUser(user);
        }
        // if action delete
        else if (type.equals("Delete")) {
            userService.delete(user);
        }

        return "redirect:/admin/";
    }
}


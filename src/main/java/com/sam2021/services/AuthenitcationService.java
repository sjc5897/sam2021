package com.sam2021.services;

import com.sam2021.database.UserEntity;
import com.sam2021.database.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The service for Login, this assists the controller in authentication
 * Language: Java 13
 * Framework: Spring
 * Author: Stephen Cook <sjc5897@rit.edu>
 * Created: 10/24/20
 * Last Edit: 11/14/20
 */
@Service
public class AuthenitcationService {
    @Autowired
    UserRepository userRepository;

    /**
     * This will assist the controller in user Authentication, currently spoofed
     *
     * @param uid String representing the user id
     * @return Boolean representing if the user is authenticated
     */
    public UserEntity getUser(String uid) {
        // TODO: Implement validation for real
        List<UserEntity> res = userRepository.findByEmail(uid);
        if (res.size() == 0) {
            return null;
        } else {
            return res.get(0);
        }
    }

    /**
     * Common method to authenticate user session
     * @param session   the passed session
     * @param a_role    the access role
     * @return          String representing redirect or no issue
     */
    public String auth(HttpSession session, String a_role){
        if(session.isNew()){
            return "redirect:/login";
        }
        Long uid = (Long) session.getAttribute("uid");
        String role = (String) session.getAttribute("role");
        if(!role.equals(a_role)){
            return "redirect:/" + role;
        }
        return null;
    }


}
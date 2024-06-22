package com.swp_project_g4.Controller;

import com.swp_project_g4.Database.LearnerDAO;
import com.swp_project_g4.Model.GooglePojo;
import com.swp_project_g4.Model.Learner;
import com.swp_project_g4.Service.*;
import com.swp_project_g4.Service.model.AdminService;
import com.swp_project_g4.Service.model.InstructorService;
import com.swp_project_g4.Service.model.LearnerService;
import com.swp_project_g4.Service.model.OrganizationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@Service
//@RequestMapping("/user")
public class SignUpController {
    @Autowired
    private LearnerService learnerService;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        Learner learner = (Learner) request.getAttribute("userSignUp");
        return "user/signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupPost(HttpServletRequest request, @ModelAttribute("user") Learner learner) {
        if (learner.getCountryId() == 0) {
            learner.setCountryId(16);
        }
        learner.setPassword(MD5.getMd5(learner.getPassword()));

        if (learnerService.getByUsername(learner.getUsername()).isPresent()) {
            request.getSession().setAttribute("error", "User already exist!");
            return "redirect:./signup";
        }

        if (learnerService.getByEmail(learner.getEmail()).isPresent()) {
            request.getSession().setAttribute("error", "Email already exist!");
            return "redirect:./signup";
        }

        LearnerDAO.insertUser(learner);
        request.getSession().setAttribute("success", "Signup successful!");
        return "redirect:/login";
    }
}
package com.swp_project_g4.Controller;

import com.swp_project_g4.Database.LearnerDAO;
import com.swp_project_g4.Model.GooglePojo;
import com.swp_project_g4.Model.Instructor;
import com.swp_project_g4.Model.Learner;
import com.swp_project_g4.Repository.Repository;
import com.swp_project_g4.Service.*;
import com.swp_project_g4.Service.model.AdminService;
import com.swp_project_g4.Service.model.InstructorService;
import com.swp_project_g4.Service.model.LearnerService;
import com.swp_project_g4.Service.model.OrganizationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@Service
//@RequestMapping("/user")
public class LoginController {
    @Autowired
    private LearnerService learnerService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private InstructorService instructorService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(HttpServletRequest request, HttpServletResponse response, @RequestParam String username, @RequestParam String password, @RequestParam String account_type) {

        CookiesToken token_type = null;

        String hashed_password = MD5.getMd5(password);

        String login_password = "";
        String login_username = "";

        try {
            switch (account_type) {
                case "admin" -> {
                    var admin = adminService.getByUsername(username).orElseThrow();
                    login_password = admin.getPassword();
                    login_username = admin.getUsername();
                    token_type = CookiesToken.ADMIN;
                }
                case "learner" -> {
                    var learner = learnerService.getByUsername(username).orElseThrow();
                    login_password = learner.getPassword();
                    login_username = learner.getUsername();
                    token_type = CookiesToken.LEARNER;

                }
                case "instructor" -> {
                    var instructor = instructorService.getByUsername(username).orElseThrow();
                    login_password = instructor.getPassword();
                    login_username = instructor.getUsername();
                    token_type = CookiesToken.INSTRUCTOR;

                }
                case "organization" -> {
                    var organization = organizationService.getByUsername(username).orElseThrow();
                    login_password = organization.getPassword();
                    login_username = organization.getUsername();
                    token_type = CookiesToken.ORGANIZATION;

                }
            }
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Account does not exist");
            return "redirect:/login";
        }

        if (!login_password.equals(hashed_password)) {
            request.getSession().setAttribute("error", "Wrong password");
            return "redirect:/login";
        }

        CookieServices.loginAccount(response, login_username, login_password, token_type);
        request.getSession().setAttribute("success", "Login succeed!");

        return "redirect:/";
    }
}
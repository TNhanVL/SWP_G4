package com.swp_project_g4.Controller;

import com.swp_project_g4.Database.LearnerDAO;
import com.swp_project_g4.Model.Instructor;
import com.swp_project_g4.Model.Learner;
import com.swp_project_g4.Repository.Repository;
import com.swp_project_g4.Service.*;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@Service
//@RequestMapping("/user")
public class MainController {

    @Autowired
    private Repository repository;
    @Autowired
    private EmailService emailService;

    @InitBinder
    private void dateBinder(WebDataBinder binder) {
        //The date format to parse or output your dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        //Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam String token) {
        if (token.equals("learner")) {
            if (CookieServices.logout(request, response, "learner") &&
                    CookieServices.logout(request, response, "instructor")) {
                request.getSession().setAttribute("success", "Logout succeed!");
            } else {
                request.getSession().setAttribute("error", "Logout failed!");
            }
        } else {
            if (CookieServices.logout(request, response, "admin") &&
                    CookieServices.logout(request, response, "organization")) {
                request.getSession().setAttribute("success", "Logout succeed!");
            } else {
                request.getSession().setAttribute("error", "Logout failed!");
            }
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String mainPage(HttpServletRequest request) {
        try {
            var type = CookieServices.searchCookie(request.getCookies(), CookiesToken.ADMIN).get("usertype");
            if (type.equals(CookiesToken.ADMIN.toString()))
                return "redirect:admin/dashboard";
        } catch (Exception e) {
        }
        return "user/main";
    }

}
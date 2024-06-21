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

    @RequestMapping(value = "/checkUsername", method = RequestMethod.GET)
    @ResponseBody
    public String checkUsername(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        Learner learner = LearnerDAO.getUserByUsername(username);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (learner != null) {
            return "exist";
        } else {
            return "not exist";
        }
    }

    @RequestMapping(value = "/checkEmail", method = RequestMethod.GET)
    @ResponseBody
    public String checkEmail(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        var regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            return "exist";
        }

        Learner learner = LearnerDAO.getUserByEmail(email);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (learner != null) {
            return "exist";
        } else {
            return "not exist";
        }
    }

    @InitBinder
    private void dateBinder(WebDataBinder binder) {
        //The date format to parse or output your dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        //Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUser(ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam int userId, @ModelAttribute("user") Learner learner) {

        Learner learner1 = LearnerDAO.getUser(userId);

        if (learner1 == null) {
            request.getSession().setAttribute("error", "User not exist!");
            return "redirect:./";
        }

        learner1.setFirstName(learner.getFirstName());
        learner1.setLastName(learner.getLastName());
        learner1.setBirthday(learner.getBirthday());
        learner1.setCountryId(learner.getCountryId());
        learner1.setEmail(learner.getEmail());

        LearnerDAO.updateUser(learner1);
        request.getSession().setAttribute("success", "Update user success!");
        return "redirect:/profile/" + learner1.getUsername();
    }

    @RequestMapping(value = "/updateInstructor", method = RequestMethod.POST)
    public String updateInstructor(ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam int instructorId, @ModelAttribute("user") Learner learner) {

        Instructor instructor1 = repository.getInstructorRepository().findById(instructorId).get();

        if (instructor1 == null) {
            request.getSession().setAttribute("error", "User not exist!");
            return "redirect:./";
        }

        instructor1.setFirstName(learner.getFirstName());
        instructor1.setLastName(learner.getLastName());
        instructor1.setCountryId(learner.getCountryId());
        instructor1.setEmail(learner.getEmail());

        repository.getInstructorRepository().save(instructor1);
        request.getSession().setAttribute("success", "Update user success!");
        return "redirect:/profile/instructor/" + instructor1.getUsername();
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
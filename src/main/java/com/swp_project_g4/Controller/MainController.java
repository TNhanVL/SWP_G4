package com.swp_project_g4.Controller;

import com.swp_project_g4.Database.LearnerDAO;
import com.swp_project_g4.Model.GooglePojo;
import com.swp_project_g4.Model.Learner;
import com.swp_project_g4.Repository.Repo;
import com.swp_project_g4.Service.CookieServices;
import com.swp_project_g4.Service.GoogleUtils;
import com.swp_project_g4.Service.MD5;
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
public class MainController {

    @Autowired
    private Repo repo;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ResponseBody
    public String getAll() {
        var a = repo.getCountryRepository().findAll();
        System.out.println(a);
        return "ok";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model) {
        return "user/login";
    }

    @RequestMapping(value = "/loginWithGG", method = RequestMethod.GET)
//    @ResponseBody
    public String loginWithGG(HttpServletRequest request, HttpServletResponse response, @RequestParam String code) {
        if (code == null || code.isEmpty()) {
            request.getSession().setAttribute("error", "Error when login with Google!");
            return "redirect:/login";
        } else {
            try {
                String accessToken = GoogleUtils.getToken(code);
                GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);

                Learner learner = LearnerDAO.getUserByEmail(googlePojo.getEmail());

//                System.out.println(googlePojo);
                if (learner != null && CookieServices.loginLearner(response, learner)) {
                    request.getSession().setAttribute("success", "Login succeed!");
                    return "redirect:./";
                }

                learner = new Learner(googlePojo);

                request.setAttribute("userSignUp", learner);

            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                request.getSession().setAttribute("error", "Error when login with Google!");
                return "redirect:/login";
            }

        }
        return "user/signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        Learner learner = (Learner) request.getAttribute("userSignUp");
        return "user/signup";
    }

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

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupPost(ModelMap model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("user") Learner learner) {
        if (learner.getCountryID() == 0) {
            learner.setCountryID(16);
        }
        learner.setPassword(MD5.getMd5(learner.getPassword()));

        if (LearnerDAO.getUserByUsername(learner.getUsername()) != null) {
            request.getSession().setAttribute("error", "User already exist!");
            return "redirect:./signup";
        }

        if (LearnerDAO.getUserByEmail(learner.getEmail()) != null) {
            request.getSession().setAttribute("error", "Email already exist!");
            return "redirect:./signup";
        }

        LearnerDAO.insertUser(learner);
        request.getSession().setAttribute("success", "Signup successful!");
        return "redirect:/login";
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUser(ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam int userID, @ModelAttribute("user") Learner learner) {

        Learner learner1 = LearnerDAO.getUser(userID);

        if (learner1 == null) {
            request.getSession().setAttribute("error", "User not exist!");
            return "redirect:./";
        }

        learner1.setFirstName(learner.getFirstName());
        learner1.setLastName(learner.getLastName());
        learner1.setBirthday(learner.getBirthday());
        learner1.setCountryID(learner.getCountryID());
        learner1.setEmail(learner.getEmail());

        LearnerDAO.updateUser(learner1);
        request.getSession().setAttribute("success", "Update user success!");
        return "redirect:./profile";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(HttpServletRequest request, HttpServletResponse response, @RequestParam String username, @RequestParam String password) {
        int status = LearnerDAO.checkUser(username, password, false);

        if (status < 0) {
            request.getSession().setAttribute("error", "Some error with database!");
            return "redirect:/login";
        }

        if (status == 1) {
            request.getSession().setAttribute("error", "Username not exist!");
            return "redirect:/login";
        }

        if (status == 2) {
            request.getSession().setAttribute("error", "Incorrect password!");
            return "redirect:/login";
        }

        Learner learner = new Learner();
        learner.setUsername(username);
        learner.setPassword(MD5.getMd5(password));

        CookieServices.loginLearner(response, learner);
        request.getSession().setAttribute("success", "Login succeed!");
        return "redirect:./";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        if (CookieServices.logoutLearner(request, response)) {
            request.getSession().setAttribute("success", "Logout succeed!");
            return "redirect:/login";
        } else {
            request.getSession().setAttribute("error", "Logout failed!");
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String mainPage(ModelMap model) {
        return "user/main";
    }

}
package com.swp_project_g4.Controller;

import com.mservice.enums.RequestType;
import com.mservice.momo.MomoPay;
import com.swp_project_g4.Database.CourseDAO;
import com.swp_project_g4.Database.UserDAO;
import com.swp_project_g4.Model.Course;
import com.swp_project_g4.Model.GooglePojo;
import com.swp_project_g4.Model.User;
import com.swp_project_g4.Repository.Repo;
import com.swp_project_g4.Service.CookieServices;
import com.swp_project_g4.Service.GoogleUtils;
import com.swp_project_g4.Service.JwtUtil;
import com.swp_project_g4.Service.MD5;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@Service
//@RequestMapping("/user")
public class UserController {

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

                User user = UserDAO.getUserByEmail(googlePojo.getEmail());

//                System.out.println(googlePojo);
                if (user != null) {
                    String TokenBody = JwtUtil.generateJwt(user.getUsername(), user.getPassword());
                    System.out.println(user);
                    Cookie cookie = new Cookie("jwtToken", TokenBody);
                    cookie.setMaxAge(60 * 60 * 6);
                    request.getSession().setAttribute("success", "Login succeed!");
                    response.addCookie(cookie);
                    return "redirect:./";
                }

                user = new User(googlePojo);

                request.setAttribute("userSignUp", user);

            } catch (IOException ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                request.getSession().setAttribute("error", "Error when login with Google!");
                return "redirect:/login";
            }

        }
        return "user/signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getAttribute("userSignUp");
        return "user/signup";
    }

    @RequestMapping(value = "/checkUsername", method = RequestMethod.GET)
    @ResponseBody
    public String checkUsername(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        String username = (String) request.getParameter("username");
        User user = UserDAO.getUserByUsername(username);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (user != null) {
            return "exist";
        } else {
            return "not exist";
        }
    }

    @RequestMapping(value = "/checkEmail", method = RequestMethod.GET)
    @ResponseBody
    public String checkEmail(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        String email = (String) request.getParameter("email");
        User user = UserDAO.getUserByEmail(email);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (user != null) {
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
    public String signupPost(ModelMap model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("user") User user) {
        if (user.getCountryID() == 0) {
            user.setCountryID(16);
        }
        user.setPassword(MD5.getMd5(user.getPassword()));

        if (UserDAO.getUserByUsername(user.getUsername()) != null) {
            request.getSession().setAttribute("error", "User already exist!");
            return "redirect:./signup";
        }

        if (UserDAO.getUserByEmail(user.getEmail()) != null) {
            request.getSession().setAttribute("error", "Email already exist!");
            return "redirect:./signup";
        }

        UserDAO.insertUser(user);
        request.getSession().setAttribute("success", "Signup successful!");
        return "redirect:/login";
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUser(ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam int userID, @ModelAttribute("user") User user) {

        User user1 = UserDAO.getUser(userID);

        if (user1 == null) {
            request.getSession().setAttribute("error", "User not exist!");
            return "redirect:./";
        }

        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setBirthday(user.getBirthday());
        user1.setCountryID(user.getCountryID());
        user1.setEmail(user.getEmail());

        UserDAO.updateUser(user1);
        request.getSession().setAttribute("success", "Update user success!");
        return "redirect:./profile";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(HttpServletRequest request, HttpServletResponse response, @RequestParam String username, @RequestParam String password) {
        int status = UserDAO.checkUser(username, password, false);

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

        String TokenBody = JwtUtil.generateJwt(username, MD5.getMd5(password));
        Cookie cookie = new Cookie("jwtToken", TokenBody);
        cookie.setMaxAge(60 * 60 * 6);
        request.getSession().setAttribute("success", "Login succeed!");
        response.addCookie(cookie);
        return "redirect:./";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("jwtToken")) {
                cookie.setValue(null);
                response.addCookie(cookie);
                break;
            }
        }
        request.getSession().setAttribute("success", "Logout succeed!");
        return "redirect:/login";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String selfProfile(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        //check logged in
        if (!CookieServices.checkUserLoggedIn(request.getCookies())) {
            return "redirect:./";
        }

        User user = UserDAO.getUserByUsername(CookieServices.getUserName(request.getCookies()));

        model.addAttribute("username", user.getUsername());
        return "user/profile/profile";
    }

    @RequestMapping(value = "/profile/{username}", method = RequestMethod.GET)
    public String profile(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable String username) {
        model.addAttribute("username", username);
        return "user/profile/profile";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String mainPage(ModelMap model) {
        return "user/main";
    }

}
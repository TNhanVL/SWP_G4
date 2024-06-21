package com.swp_project_g4.Controller;

import com.swp_project_g4.Database.LearnerDAO;
import com.swp_project_g4.Model.GooglePojo;
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
    private Repository repository;
    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ResponseBody
    public String getAll() {
        var a = repository.getCountryRepository().findAll();
        System.out.println(a);
        return "ok";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
//        try {
//            var loggedIn = CookieServices.checkLoggedIn(request);
//            if (loggedIn) {
//                request.getSession().setAttribute("error", "Please logout before login into another account");
//                return "redirect:/";
//            }
//        } catch (Exception e) {
//
//        }
        return "user/login";
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
    public String forgetPasswordGet(HttpServletRequest request) {
        request.getSession().setAttribute("sentPasswordRecoveryEmail", 0);
        return "user/forgotPassword";
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    public String forgetPasswordPost(HttpServletRequest request, HttpServletResponse response, @RequestParam String email) {
        try {
            var account = repository.getLearnerRepository().findByEmail(email).orElseThrow();

            var resetToken = JwtUtil.generateJwt(account.getUsername(), account.getID() + "", CookiesToken.RESET);

            var cookie = new Cookie(CookiesToken.RESET.toString(), resetToken);


            cookie.setMaxAge(60 * 5);


            emailService.sendResetPasswordEmail(account.getID(), resetToken);

            response.addCookie(cookie);

            request.getSession().setAttribute("recoveryAccount", account);
            request.getSession().setAttribute("sentPasswordRecoveryEmail", 1);
        } catch (Exception e) {
            request.getSession().setAttribute("sentPasswordRecoveryEmail", 2);
        }
        return "user/forgotPassword";
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)

    public String resetForgotPassword(HttpServletResponse response, HttpServletRequest request, @RequestParam String token) {
        var resetCookie = CookieServices.searchCookie(request.getCookies(), CookiesToken.RESET);
        var resetClaim = JwtUtil.parseJwt(token);
        try {
            if (resetCookie.hashCode() != resetClaim.hashCode())
                throw new Exception();

            request.getSession().setAttribute("sentPasswordRecoveryEmail", 3);

        } catch (Exception e) {
            request.getSession().setAttribute("sentPasswordRecoveryEmail", 4);
        }

        return "user/forgotPassword";

    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)

    public String resetForgotPasswordPost(HttpServletResponse response, HttpServletRequest request, @RequestParam String password) {

        try {
            var resetCookie = CookieServices.searchCookie(request.getCookies(), CookiesToken.RESET);
            var id = Integer.parseInt(resetCookie.get("password").toString());
            var account = repository.getLearnerRepository().findById(id).orElseThrow();

            account.setPassword(MD5.getMd5(password));

            repository.getLearnerRepository().save(account);

            for (var cookie : request.getCookies()) {
                if (cookie.getName().equals(CookiesToken.RESET.toString())) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }

            request.getSession().setAttribute("success", "Password reset!");
            return "redirect:/login";
        } catch (Exception e) {
            request.getSession().setAttribute("sentPasswordRecoveryEmail", 4);
        }

        return "user/forgotPassword";

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
                if (learner != null
                        && CookieServices.loginAccount(response, learner.getUsername(), learner.getPassword(), CookiesToken.LEARNER)) {
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
    public String signupPost(HttpServletRequest request, @ModelAttribute("user") Learner learner) {
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

        boolean isValidInfo = UserServices.isValidInformation(learner.getFirstName() + " " + learner.getLastName(), "0939006143", learner.getEmail(), learner.getBirthday().toString());

        if (!isValidInfo) {
            request.getSession().setAttribute("error", "Invalid information!");
            return "redirect:/";
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
        return "redirect:/profile/" + learner1.getUsername();
    }

    @RequestMapping(value = "/updateInstructor", method = RequestMethod.POST)
    public String updateInstructor(ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam int instructorID, @ModelAttribute("user") Learner learner) {

        Instructor instructor1 = repository.getInstructorRepository().findById(instructorID).get();

        if (instructor1 == null) {
            request.getSession().setAttribute("error", "User not exist!");
            return "redirect:./";
        }

        instructor1.setFirstName(learner.getFirstName());
        instructor1.setLastName(learner.getLastName());
        instructor1.setCountryID(learner.getCountryID());
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
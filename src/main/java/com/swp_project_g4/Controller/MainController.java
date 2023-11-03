package com.swp_project_g4.Controller;

import com.swp_project_g4.Database.LearnerDAO;
import com.swp_project_g4.Model.GooglePojo;
import com.swp_project_g4.Model.Learner;
import com.swp_project_g4.Repository.Repo;
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
    private Repo repo;
    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ResponseBody
    public String getAll() {
        var a = repo.getCountryRepository().findAll();
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
            var account = repo.getLearnerRepository().findByEmail(email).orElseThrow();

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
            var account = repo.getLearnerRepository().findById(id).orElseThrow();

            account.setPassword(MD5.getMd5(password));

            repo.getLearnerRepository().save(account);

            for (var cookie : request.getCookies()) {
                if (cookie.getName().equals(CookiesToken.RESET.toString())) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }

            request.getSession().setAttribute("sentPasswordRecoveryEmail", 5);

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
        return "redirect:./profile";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(HttpServletRequest request, HttpServletResponse response, @RequestParam String username, @RequestParam String password, @RequestParam String account_type) {

        CookiesToken token_type = null;

        String hashed_password = MD5.getMd5(password);

        String login_password = "";
        String login_username = "";

        try {
            switch (account_type) {
                case "admin" -> {
                    var admin = repo.getAdminRepository().findByUsername(username).orElseThrow();
                    login_password = admin.getPassword();
                    login_username = admin.getUsername();
                    token_type = CookiesToken.ADMIN;
                }
                case "learner" -> {
                    var learner = repo.getLearnerRepository().findByUsername(username).orElseThrow();
                    login_password = learner.getPassword();
                    login_username = learner.getUsername();
                    token_type = CookiesToken.LEARNER;

                }
                case "instructor" -> {
                    var instructor = repo.getInstructorRepository().findByUsername(username).orElseThrow();
                    login_password = instructor.getPassword();
                    login_username = instructor.getUsername();
                    token_type = CookiesToken.INSTRUCTOR;

                }
                case "organization" -> {
                    var organization = repo.getOrganizationRepository().findByUsername(username).orElseThrow();
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


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam String token) {
        if (CookieServices.logout(request, response, token)) {
            request.getSession().setAttribute("success", "Logout succeed!");
        } else {
            request.getSession().setAttribute("error", "Logout failed!");
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
package com.swp_project_g4.Controller;

import com.swp_project_g4.Service.*;
import com.swp_project_g4.Service.model.AdminService;
import com.swp_project_g4.Service.model.InstructorService;
import com.swp_project_g4.Service.model.LearnerService;
import com.swp_project_g4.Service.model.OrganizationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Service
//@RequestMapping("/user")
public class PasswordController {
    @Autowired
    private LearnerService learnerService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private InstructorService instructorService;
    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
    public String forgetPasswordGet(HttpServletRequest request) {
        request.getSession().setAttribute("sentPasswordRecoveryEmail", 0);
        return "user/forgotPassword";
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    public String forgetPasswordPost(HttpServletRequest request, HttpServletResponse response, @RequestParam String email) {
        try {
            var account = learnerService.getByEmail(email).orElseThrow();

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
            var account = learnerService.getById(id).orElseThrow();

            account.setPassword(MD5.getMd5(password));

            learnerService.save(account);

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

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)

    public void changePassword(HttpServletResponse response, HttpServletRequest request, @RequestParam String password, @RequestParam String oldPassword, @RequestParam String username) {
        try {
            var user = learnerService.getByUsernameAndPassword(username, MD5.getMd5(oldPassword)).orElseThrow();

            CookieServices.logout(request, response, CookiesToken.LEARNER.toString());

            user.setPassword(MD5.getMd5(password));
            learnerService.save(user);

            request.getSession().setAttribute("success", "Your password has been changed, please login again");
//            return "redirect:/";
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Your password cannot be change in the moment");
        }
//        return "redirect:/profile/" + username;
    }
}
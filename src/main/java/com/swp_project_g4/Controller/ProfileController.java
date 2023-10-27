package com.swp_project_g4.Controller;

import com.swp_project_g4.Database.LearnerDAO;
import com.swp_project_g4.Model.User;
import com.swp_project_g4.Repository.Repo;
import com.swp_project_g4.Service.CookieServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Service
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private Repo repo;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String selfProfile(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        //check logged in
        if (!CookieServices.checkUserLoggedIn(request.getCookies())) {
            return "redirect:./";
        }

        User user = LearnerDAO.getUserByUsername(CookieServices.getUserNameOfLearner(request.getCookies()));

        model.addAttribute("username", user.getUsername());
        return "user/profile/profile";
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public String profile(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable String username) {
        model.addAttribute("username", username);
        return "user/profile/profile";
    }

}
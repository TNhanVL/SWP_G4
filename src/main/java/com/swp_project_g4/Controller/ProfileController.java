package com.swp_project_g4.Controller;

import com.swp_project_g4.Database.LearnerDAO;
import com.swp_project_g4.Model.Course;
import com.swp_project_g4.Model.Learner;
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

import java.util.ArrayList;

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

        Learner learner = LearnerDAO.getUserByUsername(CookieServices.getUserNameOfLearner(request.getCookies()));

        model.addAttribute("username", learner.getUsername());
        return "user/profile/profile";
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public String profile(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable String username) {
        var usernameInCookie = CookieServices.getUserNameOfLearner(request.getCookies());
        boolean guest = true;

        var learnerOptional = repo.getLearnerRepository().findByUsername(username);
        if (!learnerOptional.isPresent()) {
            request.getSession().setAttribute("error", "Not exist this username!");
            return "redirect:/";
        }
        var learner = learnerOptional.get();

        //check guest mode
        if (usernameInCookie.equals(learner.getUsername())) {
            guest = false;
        }

        var courseProgresses = repo.getCourseProgressRepository().findByLearnerID(learner.getID());
        var purchasedCourses = new ArrayList<Course>();
        for (var courseProgress : courseProgresses) {
            purchasedCourses.add(courseProgress.getCourse());
        }

        model.addAttribute("guest", guest);
        model.addAttribute("learner", learner);
        model.addAttribute("numberOfPurchasedCourses", purchasedCourses.size());
        model.addAttribute("purchasedCourses", purchasedCourses);
        return "user/profile/profile";
    }

}
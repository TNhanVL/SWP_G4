package com.swp_project_g4.Controller;

import com.swp_project_g4.Model.Course;
import com.swp_project_g4.Model.Learner;
import com.swp_project_g4.Repository.CourseProgressRepository;
import com.swp_project_g4.Repository.Repository;
import com.swp_project_g4.Service.CookieServices;
import com.swp_project_g4.Service.CookiesToken;
import com.swp_project_g4.Service.model.CourseProgressService;
import com.swp_project_g4.Service.model.CourseService;
import com.swp_project_g4.Service.MD5;
import com.swp_project_g4.Service.model.InstructorService;
import com.swp_project_g4.Service.model.LearnerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;

@Controller
@Service
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private LearnerService learnerService;
    @Autowired
    private InstructorService instructorService;
    @Autowired
    private CourseProgressService courseProgressService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String selfProfile(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        //check logged in
        if (!CookieServices.checkLearnerLoggedIn(request.getCookies())) {
            return "redirect:/";
        }

        Learner learner = learnerService.getByUsername(CookieServices.getUserNameOfLearner(request.getCookies())).get();
//        Learner learner = learnerService.getByUsername(CookieServices.getUserNameOfLearner(request.getCookies())).get();
        return "redirect:/profile/" + learner.getUsername();
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public String profile(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable String username) {
        var usernameInCookie = CookieServices.getUserNameOfLearner(request.getCookies());
        boolean guest = true;

        
        var learnerOptional = learnerService.getByUsername(username);
        if (!learnerOptional.isPresent()) {
            request.getSession().setAttribute("error", "Not exist this username!");
            return "redirect:/";
        }
        var learner = learnerOptional.get();

        //check guest mode
        if (usernameInCookie.equals(learner.getUsername())) {
            guest = false;
        }

        //get purchased courses
        var courseProgresses = courseProgressService.getByLearnerId(learner.getID());
        var purchasedCourses = new ArrayList<Course>();
        for (var courseProgress : courseProgresses) {
            purchasedCourses.add(courseProgress.getCourse());
        }

        //sum time learning
        int totalLearningTime = 0;
        for (var courseProgress : courseProgresses) {
            totalLearningTime += courseProgress.getProgressPercent() * courseProgress.getCourse().getTotalTime();
        }

        int firstYearOfLearning = (new Date()).getYear();
        //first Year of learning
        if (courseProgresses.size() > 0) {
            firstYearOfLearning = courseProgresses.get(0).getStartAt().getYear();
        }


        model.addAttribute("guest", guest);
        model.addAttribute("user", learner);
        model.addAttribute("learner", learner);
        model.addAttribute("totalLearningTime", totalLearningTime);
        model.addAttribute("numberOfPurchasedCourses", purchasedCourses.size());
        model.addAttribute("numberOfCompletedCourse", courseProgressService.getByLearnerIdAndCompleted(learner.getID(), true).size());
        model.addAttribute("firstYearOfLearning", firstYearOfLearning + 1900);
        model.addAttribute("courseProgresses", courseProgresses);
        model.addAttribute("purchasedCourses", purchasedCourses);
        return "user/profile/profile";
    }

    @RequestMapping(value = "/instructor/{username}", method = RequestMethod.GET)
    public String instructorProfile(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable String username) {
        var usernameInCookie = CookieServices.getUserNameOfInstructor(request.getCookies());
        boolean guest = true;

        var instructorOptional = instructorService.getByUsername(username);
        if (!instructorOptional.isPresent()) {
            request.getSession().setAttribute("error", "Not exist this username!");
            return "redirect:/";
        }
        var instructor = instructorOptional.get();

        //check guest mode
        if (usernameInCookie.equals(instructor.getUsername())) {
            guest = false;
        }

        //get purchased courses
        var courseProgresses = new ArrayList<Course>();
        var purchasedCourses = new ArrayList<Course>();
        var createdCourses = courseService.getAllCreatedCourses(instructor.getID());

        //sum time learning
        int totalLearningTime = 0;
        int firstYearOfLearning = (new Date()).getYear();


        model.addAttribute("guest", guest);
        model.addAttribute("user", instructor);
        model.addAttribute("learner", null);
        model.addAttribute("instructor", instructor);
        model.addAttribute("totalLearningTime", totalLearningTime);
        model.addAttribute("numberOfPurchasedCourses", purchasedCourses.size());
        model.addAttribute("numberOfCompletedCourse", 0);
        model.addAttribute("firstYearOfLearning", firstYearOfLearning + 1900);
        model.addAttribute("courseProgresses", courseProgresses);
        model.addAttribute("purchasedCourses", purchasedCourses);
        model.addAttribute("createdCourses", createdCourses);
        return "user/profile/profile";
    }
}
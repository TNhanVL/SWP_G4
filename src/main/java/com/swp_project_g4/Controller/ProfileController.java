package com.swp_project_g4.Controller;

import com.swp_project_g4.Database.LearnerDAO;
import com.swp_project_g4.Model.Course;
import com.swp_project_g4.Model.Instructor;
import com.swp_project_g4.Model.Learner;
import com.swp_project_g4.Service.CookieServices;
import com.swp_project_g4.Service.model.CourseProgressService;
import com.swp_project_g4.Service.model.CourseService;
import com.swp_project_g4.Service.model.InstructorService;
import com.swp_project_g4.Service.model.LearnerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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

        Learner learner = learnerService.findByUsername(CookieServices.getUserNameOfLearner(request.getCookies())).get();
//        Learner learner = learnerService.findByUsername(CookieServices.getUserNameOfLearner(request.getCookies())).get();
        return "redirect:/profile/" + learner.getUsername();
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public String profile(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable String username) {
        var usernameInCookie = CookieServices.getUserNameOfLearner(request.getCookies());
        boolean guest = true;

        
        var learnerOptional = learnerService.findByUsername(username);
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
        var courseProgresses = courseProgressService.getAllByLearnerId(learner.getID());
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
        model.addAttribute("numberOfCompletedCourse", courseProgressService.getAllByLearnerIdAndCompleted(learner.getID(), true).size());
        model.addAttribute("firstYearOfLearning", firstYearOfLearning + 1900);
        model.addAttribute("courseProgresses", courseProgresses);
        model.addAttribute("purchasedCourses", purchasedCourses);
        return "user/profile/profile";
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

    @RequestMapping(value = "/instructor/{username}", method = RequestMethod.GET)
    public String instructorProfile(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable String username) {
        var usernameInCookie = CookieServices.getUserNameOfInstructor(request.getCookies());
        boolean guest = true;

        var instructorOptional = instructorService.findByUsername(username);
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

    @RequestMapping(value = "/updateInstructor", method = RequestMethod.POST)
    public String updateInstructor(ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam int instructorId, @ModelAttribute("user") Learner learner) {

        Instructor instructor1 = instructorService.findById(instructorId).get();

        if (instructor1 == null) {
            request.getSession().setAttribute("error", "User not exist!");
            return "redirect:./";
        }

        instructor1.setFirstName(learner.getFirstName());
        instructor1.setLastName(learner.getLastName());
        instructor1.setCountryId(learner.getCountryId());
        instructor1.setEmail(learner.getEmail());

        instructorService.save(instructor1);
        request.getSession().setAttribute("success", "Update user success!");
        return "redirect:/profile/instructor/" + instructor1.getUsername();
    }
}
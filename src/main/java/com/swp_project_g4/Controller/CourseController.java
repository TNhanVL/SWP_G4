package com.swp_project_g4.Controller;

import com.swp_project_g4.Database.CourseDAO;
import com.swp_project_g4.Database.LearnerDAO;
import com.swp_project_g4.Model.Learner;
import com.swp_project_g4.Repository.Repo;
import com.swp_project_g4.Service.CookieServices;
import com.swp_project_g4.Service.model.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private Repo repo;
    @Autowired
    private CourseService courseService;

    @RequestMapping(value = "/deleteOrder/{courseID}", method = RequestMethod.GET)
    public String deleteOrderFromCourse(ModelMap model, HttpServletRequest request, @PathVariable int courseID) {

        //check logged in
        if (!CookieServices.checkLearnerLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:/login";
        }

        Learner learner = LearnerDAO.getUserByUsername(CookieServices.getUserNameOfLearner(request.getCookies()));

        CourseDAO.deleteCartProduct(learner.getID(), courseID);

        return "redirect:/course/" + courseID;
    }

    @RequestMapping(value = "/{courseID}", method = RequestMethod.GET)
    public String course(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int courseID) {
        //check course
        var courseOptional = repo.getCourseRepository().findById(courseID);
        if (!courseOptional.isPresent()) {
            request.getSession().setAttribute("error", "Not exist this course!");
            return "redirect:/course/all";
        }
        var course = courseOptional.get();

        var username = CookieServices.getUserNameOfLearner(request.getCookies());
        var learnerOptional = repo.getLearnerRepository().findByUsername(username);
        var learner = learnerOptional.orElse(null);
        if (learnerOptional.isPresent()) {
            var courseProgressOptional = repo.getCourseProgressRepository().findByCourseIDAndLearnerID(courseID, learner.getID());
            if (courseProgressOptional.isPresent()) {
                model.addAttribute("coursePurchased", courseID);
                model.addAttribute("courseProgress", courseProgressOptional.get());
                request.getSession().setAttribute("courseProgress", courseProgressOptional.get());
            }
        }

        var courseProgresses = repo.getCourseProgressRepository().findByCourseID(courseID);
        var instructors = courseService.getAllInstructors(courseID);

        model.addAttribute("instructors", instructors);
        model.addAttribute("numberOfPurchased", courseProgresses.size());
        model.addAttribute("courseID", courseID);
        request.getSession().setAttribute("learner", learner);
        request.getSession().setAttribute("course", course);
        request.getSession().setAttribute("courseID", courseID);
        return "user/course";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allCourses(HttpServletRequest request, HttpServletResponse response) {
        return "user/allCourses";
    }

    @GetMapping("edit/{courseID}")
    public String editCourse(ModelMap model, HttpServletRequest request, @PathVariable int courseID) {
        return "reactjs/index";
    }
}
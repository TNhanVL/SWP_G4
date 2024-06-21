package com.swp_project_g4.Controller;

import com.swp_project_g4.Database.CourseDAO;
import com.swp_project_g4.Database.LearnerDAO;
import com.swp_project_g4.Model.Course;
import com.swp_project_g4.Model.Instruct;
import com.swp_project_g4.Model.Learner;
import com.swp_project_g4.Repository.Repository;
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

import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private Repository repository;
    @Autowired
    private CourseService courseService;

    @RequestMapping(value = "/deleteOrder/{courseId}", method = RequestMethod.GET)
    public String deleteOrderFromCourse(ModelMap model, HttpServletRequest request, @PathVariable int courseId) {

        //check logged in
        if (!CookieServices.checkLearnerLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:/login";
        }

        Learner learner = LearnerDAO.getUserByUsername(CookieServices.getUserNameOfLearner(request.getCookies()));

        CourseDAO.deleteCartProduct(learner.getID(), courseId);

        return "redirect:/course/" + courseId;
    }

    @RequestMapping(value = "/{courseId}", method = RequestMethod.GET)
    public String course(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int courseId) {
        //check course
        var courseOptional = repository.getCourseRepository().findById(courseId);
        if (!courseOptional.isPresent()) {
            request.getSession().setAttribute("error", "Not exist this course!");
            return "redirect:/course/all";
        }
        var course = courseOptional.get();

        var username = CookieServices.getUserNameOfLearner(request.getCookies());
        var learnerOptional = repository.getLearnerRepository().findByUsername(username);
        var learner = learnerOptional.orElse(null);
        if (learnerOptional.isPresent()) {
            var courseProgressOptional = repository.getCourseProgressRepository().findByCourseIdAndLearnerId(courseId, learner.getID());
            if (courseProgressOptional.isPresent()) {
                model.addAttribute("coursePurchased", courseId);
                model.addAttribute("courseProgress", courseProgressOptional.get());
                request.getSession().setAttribute("courseProgress", courseProgressOptional.get());
            }
        }

        var courseProgresses = repository.getCourseProgressRepository().findAllByCourseId(courseId);
        var instructors = courseService.getAllInstructors(courseId);

        model.addAttribute("instructors", instructors);
        model.addAttribute("numberOfPurchased", courseProgresses.size());
        model.addAttribute("courseId", courseId);
        request.getSession().setAttribute("learner", learner);
        request.getSession().setAttribute("course", course);
        request.getSession().setAttribute("courseId", courseId);
        return "user/course";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allCourses(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        List<Course> courses = courseService.getAll();
        model.addAttribute("courses", courses);
        return "user/allCourses";
    }

    @GetMapping("edit/{courseId}")
    public String editCourse(ModelMap model, HttpServletRequest request, @PathVariable int courseId) {
        return "reactjs/index";
    }

    @GetMapping("create")
    public String createCourse(ModelMap model, HttpServletRequest request) {
        try {
            String username = CookieServices.getUserNameOfInstructor(request.getCookies());
            var instructor = repository.getInstructorRepository().findByUsername(username).get();
            var course = new Course();
            course.setOrganizationId(instructor.getOrganizationId());
            course.setName("New course");
            course = repository.getCourseRepository().save(course);
            Instruct instruct = new Instruct();
            instruct.setCourseId(course.getID());
            instruct.setInstructorId(instructor.getID());
            repository.getInstructRepository().save(instruct);
            return "redirect:/course/edit/" + course.getID();
        } catch (Exception e) {

        }
        request.getSession().setAttribute("error", "Can not create new course!");
        return "redirect:/";
    }
}
package com.swp_project_g4.Controller;

import com.swp_project_g4.Model.Course;
import com.swp_project_g4.Model.Instructor;
import com.swp_project_g4.Model.Learner;
import com.swp_project_g4.Model.Organization;
import com.swp_project_g4.Service.CookieServices;
import com.swp_project_g4.Service.MD5;
import com.swp_project_g4.Service.model.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private LearnerService learnerService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private InstructorService instructorService;
    @Autowired
    private InstructService instructService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseProgressService courseProgressService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private CookieServices cookieServices;

    @GetMapping("")
    public String redirect(HttpServletRequest request) {
        if (!cookieServices.checkAdminLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:/login";
        }

        return "redirect:admin/dashboard";
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard(ModelMap model, HttpServletRequest request) {
        request.getSession().setAttribute("learnerList", learnerService.findAll());
        request.getSession().setAttribute("orgList", organizationService.findAll());
        request.getSession().setAttribute("instructorsList", instructorService.findAll());
        request.getSession().setAttribute("courseList", courseService.findAll());
        return "admin/dashboard";
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

    @RequestMapping(value = "/editUser", method = RequestMethod.GET)
    public String editUser(ModelMap model, HttpServletRequest request, @RequestParam String id) {
        try {
            var user_id = Integer.parseInt(id);
            var user = learnerService.findById(user_id).orElseThrow();
            var course_progress = courseProgressService.findAllByLearnerId(user_id);
            request.getSession().setAttribute("currentUser", user);
            request.getSession().setAttribute("countryList", countryService.findAll());
            request.getSession().setAttribute("courseProgress", course_progress);
            request.getSession().setAttribute("addUser", false);


            ArrayList<Course> courseList = new ArrayList<>();
            for (var course : course_progress) {
                courseList.add(courseService.findById(course.getCourseId()).orElseThrow());
            }

            request.getSession().setAttribute("courseList", courseList);


        } catch (NoSuchElementException ex) {
            request.getSession().setAttribute("error", "No such user information!");
            return "redirect:./dashboard";
        } catch (NumberFormatException ex) {
            request.getSession().setAttribute("error", "Failed to load user information!");
            return "redirect:./dashboard";
        }
        return "admin/editUser";
    }

    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public String editUserPost(ModelMap model, HttpServletRequest request, @RequestParam String id, @ModelAttribute("user") Learner learner) {

        if (!cookieServices.checkAdminLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:./login";
        }

        try {
            var user = learnerService.findById(learner.getID()).orElseThrow();

            if (!user.getPassword().equals(learner.getPassword()))
                user.setPassword(MD5.getMd5(learner.getPassword()));

            user.setUsername(learner.getUsername());
            user.setFirstName(learner.getFirstName());
            user.setLastName(learner.getLastName());
            user.setEmail(learner.getEmail());
            user.setBirthday(learner.getBirthday());
            user.setCountryId(learner.getCountryId());
            user.setStatus(learner.getStatus());

            learnerService.save(user);

            request.getSession().setAttribute("success", "Update learner information succeed!");

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "There are some error when update User information!");
            return "redirect:./dashboard";
        }
        return "redirect:./editUser?id=" + learner.getID();
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public String deleteUser(ModelMap model, @RequestParam String id, HttpServletRequest request) {

        //check logged in
        if (!cookieServices.checkAdminLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:/login";
        }

        try {
            learnerService.deleteById(Integer.parseInt(id));
            request.getSession().setAttribute("success", "Delete user succeed!");
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "There are some errors when delete user!");
        }

        return "redirect:./dashboard";
    }

    @RequestMapping(value = "/editOrganization", method = RequestMethod.GET)
    public String editOrganization(ModelMap model, HttpServletRequest request, @RequestParam String id) {
        try {
            var organization_id = Integer.parseInt(id);
            var organization = organizationService.findById(organization_id).orElseThrow();
            request.getSession().setAttribute("currentOrg", organization);
            request.getSession().setAttribute("countryList", countryService.findAll());
            var courseList = courseService.findAllByOrganizationId(organization_id);
            request.getSession().setAttribute("courseList", courseList);
            request.getSession().setAttribute("instructorsList", instructorService.findAllByOrganizationId(organization_id));

        } catch (Exception e) {
            request.getSession().setAttribute("error", "Failed to load organization");
            return "redirect:./dashboard";
        }
        return "admin/editOrganization";
    }

    @RequestMapping(value = "/editOrganization", method = RequestMethod.POST)
    public String editOrganizationPost(HttpServletRequest request, @ModelAttribute("organization") Organization organization) {
        //check logged in
        if (!cookieServices.checkAdminLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:./login";
        }

        try {

            var user = organizationService.findById(organization.getID()).orElseThrow();

            user.setName(organization.getName());
            user.setUsername(organization.getUsername());
            user.setEmail(organization.getEmail());
            user.setDescription(organization.getDescription());
            user.setCountryId(organization.getCountryId());

            if (!user.getPassword().equals(organization.getPassword()))
                organization.setPassword(MD5.getMd5(organization.getPassword()));

            organizationService.save(user);
//            if (ok) {
            request.getSession().setAttribute("success", "Update organization information succeed!");
//            } else {
//                request.getSession().setAttribute("error", "Update organization information failed!");
//            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "There are some error when update organization information!");
            return "redirect:./dashboard";
        }
        return "redirect:./editOrganization?id=" + organization.getID();
    }


    @RequestMapping(value = "/editInstructor", method = RequestMethod.GET)
    public String editInstructor(HttpServletRequest request, @RequestParam String id) {
        try {
            var user_id = Integer.parseInt(id);
            var user = instructorService.findById(user_id).orElseThrow();
            request.getSession().setAttribute("currentUser", user);
            request.getSession().setAttribute("countryList", countryService.findAll());
            var instructed_course = instructService.findAllByInstructorId(user_id);

            ArrayList<Course> courseList = new ArrayList<>();
            for (var course : instructed_course) {
                courseList.add(courseService.findById(course.getCourseId()).orElseThrow());
            }

            request.getSession().setAttribute("courseList", courseList);

        } catch (Exception ex) {
            request.getSession().setAttribute("error", "Failed to load instructor information!");
            return "redirect:./dashboard";
        }
        return "admin/editInstructor";
    }

    @RequestMapping(value = "/editInstructor", method = RequestMethod.POST)
    public String editInstructor(HttpServletRequest request, @ModelAttribute("user") Instructor instructor) {
        //check logged in
        if (!cookieServices.checkAdminLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:./login";
        }

        try {
            var user = instructorService.findById(instructor.getID()).orElseThrow();

            if (!user.getPassword().equals(instructor.getPassword()))
                user.setPassword(MD5.getMd5(instructor.getPassword()));

            user.setUsername(instructor.getUsername());
            user.setCountryId(instructor.getCountryId());
            user.setFirstName(instructor.getFirstName());
            user.setLastName(instructor.getLastName());
            user.setStatus(instructor.getStatus());
            user.setEmail(instructor.getEmail());

            instructorService.save(user);
            request.getSession().setAttribute("success", "Update instructor information succeed!");

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "There are some error when update User information!");
            return "redirect:./dashboard";
        }
        return "redirect:./editInstructor?id=" + instructor.getID();
    }


    @PostMapping("addLearner")
    public String addLearner(
            @ModelAttribute("organization") Learner learner, HttpServletRequest request) {
        try {
            learner.setPassword(MD5.getMd5(learner.getPassword()));
            learner.setCountry(countryService.findById(learner.getCountryId()).orElseThrow());
            learnerService.save(learner);
        } catch (Exception e) {
            request.getSession().setAttribute("error", "There are some error when add new learner!");
        }
        return "redirect:./dashboard";
    }

    @RequestMapping(value = "/addLearner", method = RequestMethod.GET)
    public String addLearnerGET(HttpServletRequest request) {
        try {
            var user = new Learner();
            user.setID((int) learnerService.count() + 1);
            request.getSession().setAttribute("currentUser", user);
            request.getSession().setAttribute("countryList", countryService.findAll());
            request.getSession().setAttribute("addUser", true);
        } catch (NoSuchElementException ex) {
            request.getSession().setAttribute("error", "No such user information!");
            return "redirect:./dashboard";
        } catch (NumberFormatException ex) {
            request.getSession().setAttribute("error", "Failed to load user information!");
            return "redirect:./dashboard";
        }
        return "admin/editUser";
    }

}

package com.swp_project_g4.Controller;

import com.swp_project_g4.Database.AdminDAO;
import com.swp_project_g4.Database.CourseDAO;
import com.swp_project_g4.Database.OrganizationDAO;
import com.swp_project_g4.Database.LearnerDAO;
import com.swp_project_g4.Model.Learner;
import com.swp_project_g4.Model.Organization;
import com.swp_project_g4.Repository.Repo;
import com.swp_project_g4.Service.CookieServices;
import com.swp_project_g4.Service.MD5;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private Repo repo;

    @GetMapping("")
    public String redirect(HttpServletRequest request) {
        if (!CookieServices.checkAdminLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:admin/login";
        }

        return "redirect:admin/dashboard";
    }

    @GetMapping("/login")
//    @ResponseBody
    public String login(ModelMap model) {
//        model.addAttribute("title", "Index!");
        return "admin/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    @ResponseBody
    public String loginPost(HttpServletRequest request, HttpServletResponse response, @RequestParam String username, @RequestParam String password) {
//        model.addAttribute("title", "Index!");
        int status = AdminDAO.checkAdmin(username, password, false);
        if (status == 1) {
            request.getSession().setAttribute("error", "Username not exist!");
            return "redirect:./login";
        }

        if (status == 2) {
            request.getSession().setAttribute("error", "Incorrect password!");
            return "redirect:./login";
        }

        Learner learner = new Learner();
        learner.setUsername(username);
        learner.setPassword(MD5.getMd5(password));

        CookieServices.loginAdmin(response, learner);
        request.getSession().setAttribute("success", "Login succeed!");
        return "redirect:./dashboard";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
//        model.addAttribute("title", "Index!");
        CookieServices.logoutAdmin(request, response);
        request.getSession().setAttribute("success", "Logout succeed!");
        return "redirect:./login";
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard(ModelMap model, HttpServletRequest request) {
        request.getSession().setAttribute("userList", LearnerDAO.getAllUsers());
        request.getSession().setAttribute("orgList", OrganizationDAO.getAllOrganization());
        request.getSession().setAttribute("courseList", CourseDAO.getAllCourses());
        return "admin/dashboard";
    }

    @RequestMapping(value = "/editUser", method = RequestMethod.GET)
    public String editUser(ModelMap model, HttpServletRequest request, @RequestParam String id) {
        try {
            var user_id = Integer.parseInt(id);
            var user = repo.getLearnerRepository().findById(user_id).orElseThrow();
            request.getSession().setAttribute("currentUser", user);
        } catch (NoSuchElementException ex) {
            request.getSession().setAttribute("error", "No such user information!");
            return "redirect:./dashboard";
        } catch (NumberFormatException ex) {
            request.getSession().setAttribute("error", "Failed to load user information!");
            return "redirect:./dashboard";
        }
        return "admin/editUser";
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

    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public String editUserPost(ModelMap model, HttpServletRequest request, @RequestParam String id, @ModelAttribute("user") Learner learner) {

        learner.setPassword(MD5.getMd5(learner.getPassword()));
        //check logged in
        if (!CookieServices.checkAdminLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:./login";
        }

        try {
            boolean ok = LearnerDAO.updateUser(learner);
            if (ok) {
                request.getSession().setAttribute("success", "Update User information succeed!");
            } else {
                request.getSession().setAttribute("error", "Update User information failed!");
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "There are some error when update User information!");
            return "redirect:./dashboard";
        }
        return "redirect:./editUser?id=" + learner.getID();
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public String deleteUser(ModelMap model, @RequestParam String id, HttpServletRequest request) {

        //check logged in
        if (!CookieServices.checkAdminLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:/login";
        }

        try {
            if (LearnerDAO.deleteUser(Integer.parseInt(id))) {
                request.getSession().setAttribute("success", "Delete user succeed!");
            } else {
                request.getSession().setAttribute("error", "Delete user failed!");
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "There are some errors when delete user!");
        }

        return "redirect:./dashboard";
    }

    @RequestMapping(value = "/editOrganization", method = RequestMethod.GET)
    public String editOrganization(ModelMap model, HttpServletRequest request, @RequestParam String id) {
        try {
            var organization_id = Integer.parseInt(id);
            var organization = repo.getOrganizationRepository().findById(organization_id).orElseThrow();
            request.getSession().setAttribute("currentOrg", organization);
            request.getSession().setAttribute("countryList", repo.getCountryRepository().findAll());

        } catch (Exception e) {
            request.getSession().setAttribute("error", "Failed to load organization");
            return "redirect:./dashboard";
        }
        return "admin/editOrganization";
    }

    @RequestMapping(value = "/editOrganization", method = RequestMethod.POST)
    public String editOrganizationPost(HttpServletRequest request, @ModelAttribute("organization") Organization organization) {

        organization.setPassword(MD5.getMd5(organization.getPassword()));
        //check logged in
        if (!CookieServices.checkAdminLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:./login";
        }

        try {
            boolean ok = OrganizationDAO.updateOrganization(organization);
            if (ok) {
                request.getSession().setAttribute("success", "Update organization information succeed!");
            } else {
                request.getSession().setAttribute("error", "Update organization information failed!");
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "There are some error when update organization information!");
            return "redirect:./dashboard";
        }
        return "redirect:./editOrganization?id=" + organization.getID();
    }
}

package com.swp_project_g4.Controller;

import com.swp_project_g4.Database.CourseDAO;
import com.swp_project_g4.Database.LearnerDAO;
import com.swp_project_g4.Model.Learner;
import com.swp_project_g4.Service.CookieServices;
import com.swp_project_g4.Service.model.LearnerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private LearnerService learnerService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String cart(ModelMap model) {
        return "user/cart";
    }

    @RequestMapping(value = "/add/{courseId}", method = RequestMethod.GET)
    public String addToCartByCourseId(ModelMap model, HttpServletRequest request, @PathVariable int courseId) {

        //check logged in
        if (!CookieServices.checkLearnerLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:/login";
        }

        Learner learner = learnerService.getByUsername(CookieServices.getUserNameOfLearner(request.getCookies())).get();

        CourseDAO.insertCartProduct(learner.getID(), courseId);

        return "redirect:/course/" + courseId;
    }

    @RequestMapping(value = "/delete/{courseId}", method = RequestMethod.GET)
    public String deleteFromCartByCourseId(ModelMap model, HttpServletRequest request, @PathVariable int courseId) {

        //check logged in
        if (!CookieServices.checkLearnerLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:/login";
        }

        Learner learner = learnerService.getByUsername(CookieServices.getUserNameOfLearner(request.getCookies())).get();

        CourseDAO.deleteCartProduct(learner.getID(), courseId);

        return "redirect:/cart";
    }

}
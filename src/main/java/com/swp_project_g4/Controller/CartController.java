package com.swp_project_g4.Controller;

import com.swp_project_g4.Model.Cart;
import com.swp_project_g4.Model.Course;
import com.swp_project_g4.Model.Learner;
import com.swp_project_g4.Service.CookieServices;
import com.swp_project_g4.Service.model.CartService;
import com.swp_project_g4.Service.model.LearnerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private LearnerService learnerService;
    @Autowired
    private CartService cartService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String cart(ModelMap model, HttpServletRequest request) {
        Learner learner = learnerService.findByUsername(CookieServices.getUserNameOfLearner(request.getCookies())).get();
        var carts = cartService.findAllByLearnerId(learner.getID());
        var courses = new ArrayList<Course>();
        for (var cart: carts) {
            courses.add(cart.getCourse());
        }
        model.addAttribute("courses", courses);
        return "user/cart";
    }

    @RequestMapping(value = "/add/{courseId}", method = RequestMethod.GET)
    public String addToCartByCourseId(ModelMap model, HttpServletRequest request, @PathVariable int courseId) {

        //check logged in
        if (!CookieServices.checkLearnerLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:/login";
        }

        Learner learner = learnerService.findByUsername(CookieServices.getUserNameOfLearner(request.getCookies())).get();

        Cart cart = new Cart(courseId, learner.getID());
        cartService.save(cart);

        return "redirect:/course/" + courseId;
    }

    @RequestMapping(value = "/delete/{courseId}", method = RequestMethod.GET)
    public String deleteFromCartByCourseId(ModelMap model, HttpServletRequest request, @PathVariable int courseId) {

        //check logged in
        if (!CookieServices.checkLearnerLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:/login";
        }

        Learner learner = learnerService.findByUsername(CookieServices.getUserNameOfLearner(request.getCookies())).get();

        cartService.deleteByCourseIdAndLearnerId(courseId, learner.getID());

        return "redirect:/cart";
    }

}
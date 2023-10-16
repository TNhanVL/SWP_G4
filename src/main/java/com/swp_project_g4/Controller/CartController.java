package com.swp_project_g4.Controller;

import com.swp_project_g4.Database.CourseDAO;
import com.swp_project_g4.Database.UserDAO;
import com.swp_project_g4.Model.User;
import com.swp_project_g4.Service.CookieServices;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/cart")
public class CartController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String cart(ModelMap model) {
        return "user/cart";
    }

    @RequestMapping(value = "/add/{courseID}", method = RequestMethod.GET)
    public String addOrderByCourseID(ModelMap model, HttpServletRequest request, @PathVariable int courseID) {

        //check logged in
        if (!CookieServices.checkUserLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:/login";
        }

        User user = UserDAO.getUserByUsername(CookieServices.getUserName(request.getCookies()));

        CourseDAO.insertCartProduct(user.getID(), courseID);

        return "redirect:/course/" + courseID;
    }

    @RequestMapping(value = "/delete/{courseID}", method = RequestMethod.GET)
    public String deleteOrderFromCart(ModelMap model, HttpServletRequest request, @PathVariable int courseID) {

        //check logged in
        if (!CookieServices.checkUserLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:/login";
        }

        User user = UserDAO.getUserByUsername(CookieServices.getUserName(request.getCookies()));

        CourseDAO.deleteCartProduct(user.getID(), courseID);

        return "redirect:/cart";
    }

}
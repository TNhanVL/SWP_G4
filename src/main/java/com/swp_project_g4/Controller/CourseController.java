package com.swp_project_g4.Controller;

import com.mservice.enums.RequestType;
import com.mservice.momo.MomoPay;
import com.swp_project_g4.Database.*;
import com.swp_project_g4.Model.*;
import com.swp_project_g4.Service.CookieServices;
import com.swp_project_g4.Service.GoogleUtils;
import com.swp_project_g4.Service.JwtUtil;
import com.swp_project_g4.Service.MD5;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/course")
public class CourseController {

    @RequestMapping(value = "/addOrder/{courseID}", method = RequestMethod.GET)
    public String addOrderFromCourse(ModelMap model, HttpServletRequest request, @PathVariable int courseID) {

        //check logged in
        if (!CookieServices.checkUserLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:/login";
        }

        User user = UserDAO.getUserByUsername(CookieServices.getUserName(request.getCookies()));

        CourseDAO.insertCartProduct(user.getID(), courseID);

        return "redirect:/course/" + courseID;
    }

    @RequestMapping(value = "/deleteOrder/{courseID}", method = RequestMethod.GET)
    public String deleteOrderFromCourse(ModelMap model, HttpServletRequest request, @PathVariable int courseID) {

        //check logged in
        if (!CookieServices.checkUserLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:/login";
        }

        User user = UserDAO.getUserByUsername(CookieServices.getUserName(request.getCookies()));

        CourseDAO.deleteCartProduct(user.getID(), courseID);

        return "redirect:/course/" + courseID;
    }

    @RequestMapping(value = "/{courseID}", method = RequestMethod.GET)
    public String course(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int courseID) {
        model.addAttribute("courseID", courseID);
        return "user/course";
    }

}
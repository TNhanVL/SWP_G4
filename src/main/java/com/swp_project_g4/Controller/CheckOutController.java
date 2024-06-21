package com.swp_project_g4.Controller;

import com.mservice.enums.RequestType;
import com.mservice.momo.MomoPay;
import com.swp_project_g4.Database.CourseDAO;
import com.swp_project_g4.Database.LearnerDAO;
import com.swp_project_g4.Model.Course;
import com.swp_project_g4.Model.CourseProgress;
import com.swp_project_g4.Model.Learner;
import com.swp_project_g4.Repository.Repository;
import com.swp_project_g4.Service.CookieServices;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/checkOut")
public class CheckOutController {

    @Autowired
    private Repository repository;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String checkOutPost(ModelMap model, HttpServletRequest request) {

        //check logged in
        if (!CookieServices.checkLearnerLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:/login";
        }

        Learner learner = LearnerDAO.getUserByUsername(CookieServices.getUserNameOfLearner(request.getCookies()));

        //get all courses ID
        String[] courseIDStrs = request.getParameterValues("course");
        ArrayList<Course> courses = new ArrayList<>();

        if (courseIDStrs != null) {
            for (String courseIDStr : courseIDStrs) {
                try {
                    int courseID = Integer.parseInt(courseIDStr);

                    //check in cart
                    if (!CourseDAO.checkCartProduct(learner.getID(), courseID)) {
                        continue;
                    }

                    if (CourseDAO.existCourse(courseID)) {
                        courses.add(CourseDAO.getCourse(courseID));
                    }
                } catch (NumberFormatException e) {
                    System.out.println(e);
                }
            }
        }

        model.addAttribute("courses", courses);

        if (courses.isEmpty()) {
            request.getSession().setAttribute("error", "No chosing cart to checkout!");
            return "redirect:/cart";
        }

        return "user/checkOut";
    }

    @RequestMapping(value = "/withPayment", method = RequestMethod.POST)
    public String checkOutWithPayment(ModelMap model, HttpServletRequest request, @RequestParam long price, @RequestParam String paymentMethod) {

        //check logged in
        if (!CookieServices.checkLearnerLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:/login";
        }

        Learner learner = LearnerDAO.getUserByUsername(CookieServices.getUserNameOfLearner(request.getCookies()));

        //get all courses ID
        String[] courseIDStrs = request.getParameterValues("course");

        //get pay type
        RequestType requestType;
        if ("captureWallet".equals(paymentMethod)) {
            requestType = RequestType.CAPTURE_WALLET;
        } else {
            requestType = RequestType.PAY_WITH_ATM;
        }

        String payLink = MomoPay.getPayLink(request, requestType, learner.getID(), courseIDStrs, price);

        if (payLink == null) {
            request.getSession().setAttribute("error", "There are some error when checkout!");
            return "redirect:/cart";
        } else {
            return "redirect:" + payLink;
        }
    }

    @RequestMapping(value = "/finishedPayment", method = RequestMethod.GET)
    public String finishedPayment(ModelMap model, HttpServletRequest request, @RequestParam String userID, @RequestParam int resultCode, @RequestParam String message) {

        Learner learner = null;

        if(!message.equals("Successful.")){
            request.getSession().setAttribute("error", "Failed when pay the course!");
            return "redirect:/cart";
        }

        try {
            learner = LearnerDAO.getUser(Integer.parseInt(userID));
            if (resultCode != 0) {
                throw new Exception();
            }
        } catch (NumberFormatException e) {
            System.out.println(e);
            request.getSession().setAttribute("error", "There are some error!");
            return "redirect:/";
        } catch (Exception ex) {
            Logger.getLogger(CheckOutController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //get all courses ID
        String[] courseIDStrs = request.getParameterValues("course");

        if (courseIDStrs != null) {
            for (String courseIDStr : courseIDStrs) {
                try {
                    int courseID = Integer.parseInt(courseIDStr);

                    //check in cart
                    if (!CourseDAO.checkCartProduct(learner.getID(), courseID)) {
                        continue;
                    }

                    CourseDAO.deleteCartProduct(learner.getID(), courseID);
                    repository.getCourseProgressRepository().save(new CourseProgress(learner.getID(), courseID));

                } catch (NumberFormatException e) {
                    System.out.println(e);
                }
            }
        }

        request.getSession().setAttribute("success", "Pay successful!");

        return "redirect:/profile";
    }

}
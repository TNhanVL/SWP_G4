package com.swp_project_g4.Controller;

import com.mservice.enums.RequestType;
import com.mservice.momo.MomoPay;
import com.swp_project_g4.Model.Course;
import com.swp_project_g4.Model.CourseProgress;
import com.swp_project_g4.Model.Learner;
import com.swp_project_g4.Service.CookieServices;
import com.swp_project_g4.Service.model.CartService;
import com.swp_project_g4.Service.model.CourseProgressService;
import com.swp_project_g4.Service.model.CourseService;
import com.swp_project_g4.Service.model.LearnerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/checkOut")
public class PaymentController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private LearnerService learnerService;
    @Autowired
    private CourseProgressService courseProgressService;
    @Autowired
    private CartService cartService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String checkOutPost(ModelMap model, HttpServletRequest request) {

        //check logged in
        if (!CookieServices.checkLearnerLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:/login";
        }

        Learner learner = learnerService.findByUsername(CookieServices.getUserNameOfLearner(request.getCookies())).get();

        //get all courses ID
        String[] courseIdStrs = request.getParameterValues("course");
        ArrayList<Course> courses = new ArrayList<>();

        if (courseIdStrs != null) {
            for (String courseIdStr : courseIdStrs) {
                try {
                    int courseId = Integer.parseInt(courseIdStr);


                    //check in cart

                    if (cartService.findByCourseIdAndLearnerId(courseId, learner.getID()).isEmpty()) {
                        continue;
                    }


                    if (courseService.findById(courseId).isPresent()) {
                        courses.add(courseService.findById(courseId).get());
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


        Learner learner = learnerService.findByUsername(CookieServices.getUserNameOfLearner(request.getCookies())).get();

        //get all courses ID
        String[] courseIdStrs = request.getParameterValues("course");

        //get pay type
        RequestType requestType;
        if ("captureWallet".equals(paymentMethod)) {
            requestType = RequestType.CAPTURE_WALLET;
        } else {
            requestType = RequestType.PAY_WITH_ATM;
        }

        String payLink = MomoPay.getPayLink(request, requestType, learner.getID(), courseIdStrs, price);

        if (payLink == null) {
            request.getSession().setAttribute("error", "There are some error when checkout!");
            return "redirect:/cart";
        } else {
            return "redirect:" + payLink;
        }
    }

    @RequestMapping(value = "/finishedPayment", method = RequestMethod.GET)
    public String finishedPayment(ModelMap model, HttpServletRequest request, @RequestParam String userId, @RequestParam int resultCode, @RequestParam String message) {

        Learner learner = null;

        if (!message.equals("Successful.")) {
            request.getSession().setAttribute("error", "Failed when pay the course!");
            return "redirect:/cart";
        }

        try {
            learner = learnerService.findById(Integer.parseInt(userId)).get();
            if (resultCode != 0) {
                throw new Exception();
            }
        } catch (NumberFormatException e) {
            System.out.println(e);
            request.getSession().setAttribute("error", "There are some error!");
            return "redirect:/";
        } catch (Exception ex) {
            Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //get all courses ID
        String[] courseIdStrs = request.getParameterValues("course");

        if (courseIdStrs != null) {
            for (String courseIdStr : courseIdStrs) {
                try {
                    int courseId = Integer.parseInt(courseIdStr);

                    //check in cart
                    if (cartService.findByCourseIdAndLearnerId(courseId, learner.getID()).isEmpty()) {
                        continue;
                    }

                    cartService.deleteByCourseIdAndLearnerId(courseId, learner.getID());
                    courseProgressService.save(new CourseProgress(learner.getID(), courseId));

                } catch (NumberFormatException e) {
                    System.out.println(e);
                }
            }
        }

        request.getSession().setAttribute("success", "Pay successful!");

        return "redirect:/profile";
    }

}
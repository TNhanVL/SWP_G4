package com.swp_project_g4.Controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CourseController {
    
    @RequestMapping(value = "/course/addOrder/{courseID}", method = RequestMethod.GET)
    public String addOrderFromCourse(ModelMap model, HttpServletRequest request, @PathVariable int courseID) {

        //check logged in
        if (!CookieServices.checkUserLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:../../login";
        }

        User user = UserDAO.getUserByUsername(CookieServices.getUserName(request.getCookies()));

        CourseDAO.insertOrderCourse(user.getID(), courseID);

        return "redirect:../../course/" + courseID;
    }

    @RequestMapping(value = "/course/deleteOrder/{courseID}", method = RequestMethod.GET)
    public String deleteOrderFromCourse(ModelMap model, HttpServletRequest request, @PathVariable int courseID) {

        //check logged in
        if (!CookieServices.checkUserLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:../../login";
        }

        User user = UserDAO.getUserByUsername(CookieServices.getUserName(request.getCookies()));

        CourseDAO.deleteOrderCourse(user.getID(), courseID);

        return "redirect:../../course/" + courseID;
    }

    @RequestMapping(value = "/cart/deleteOrder/{courseID}", method = RequestMethod.GET)
    public String deleteOrderFromCart(ModelMap model, HttpServletRequest request, @PathVariable int courseID) {

        //check logged in
        if (!CookieServices.checkUserLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:../../login";
        }

        User user = UserDAO.getUserByUsername(CookieServices.getUserName(request.getCookies()));

        CourseDAO.deleteOrderCourse(user.getID(), courseID);

        return "redirect:../../cart";
    }

    @RequestMapping(value = "/checkOut", method = RequestMethod.POST)
    public String checkOutPost(ModelMap model, HttpServletRequest request) {

        //check logged in
        if (!CookieServices.checkUserLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:./login";
        }

        User user = UserDAO.getUserByUsername(CookieServices.getUserName(request.getCookies()));

        //get all courses ID
        String[] courseIDStrs = request.getParameterValues("course");
        ArrayList<Course> courses = new ArrayList<>();

        if (courseIDStrs != null) {
            for (String courseIDStr : courseIDStrs) {
                try {
                    int courseID = Integer.parseInt(courseIDStr);

                    //check in cart
                    if (!CourseDAO.checkOrderCourse(user.getID(), courseID)) {
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
            return "redirect:./cart";
        }

        return "user/checkOut";
    }

    @RequestMapping(value = "/checkOutWithPayment", method = RequestMethod.POST)
    public String checkOutWithPayment(ModelMap model, HttpServletRequest request, @RequestParam long price, @RequestParam String paymentMethod) {

        //check logged in
        if (!CookieServices.checkUserLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:./login";
        }

        User user = UserDAO.getUserByUsername(CookieServices.getUserName(request.getCookies()));

        //get all courses ID
        String[] courseIDStrs = request.getParameterValues("course");

        //get pay type
        RequestType requestType;
        if ("captureWallet".equals(paymentMethod)) {
            requestType = RequestType.CAPTURE_WALLET;
        } else {
            requestType = RequestType.PAY_WITH_ATM;
        }

        String payLink = MomoPay.getPayLink(request, requestType, user.getID(), courseIDStrs, price);

        if (payLink == null) {
            request.getSession().setAttribute("error", "There are some error when checkout!");
            return "redirect:./cart";
        } else {
            return "redirect:" + payLink;
        }
    }

    @RequestMapping(value = "/finishedPayment", method = RequestMethod.GET)
    public String finishedPayment(ModelMap model, HttpServletRequest request, @RequestParam String userID, @RequestParam int resultCode) {

        User user = null;

        try {
            user = UserDAO.getUser(Integer.parseInt(userID));
            if (resultCode != 0) {
                throw new Exception();
            }
        } catch (NumberFormatException e) {
            System.out.println(e);
            request.getSession().setAttribute("error", "There are some error!");
            return "redirect:./";
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //get all courses ID
        String[] courseIDStrs = request.getParameterValues("course");

        if (courseIDStrs != null) {
            for (String courseIDStr : courseIDStrs) {
                try {
                    int courseID = Integer.parseInt(courseIDStr);

                    //check in cart
                    if (!CourseDAO.checkOrderCourse(user.getID(), courseID)) {
                        continue;
                    }

                    CourseDAO.deleteOrderCourse(user.getID(), courseID);
                    CourseDAO.insertPurchasedCourse(user.getID(), courseID);

                } catch (NumberFormatException e) {
                    System.out.println(e);
                }
            }
        }

        request.getSession().setAttribute("success", "Pay successful!");

        return "redirect:./profile";
    }

    @RequestMapping(value = "/learn/{courseID}", method = RequestMethod.GET)
    public String lesson(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int courseID) {
        model.addAttribute("courseID", courseID);
        return "user/lesson";
    }

    @RequestMapping(value = "/learn/{courseID}/{lessonID}", method = RequestMethod.GET)
    public String lesson(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int courseID, @PathVariable int lessonID) {
        model.addAttribute("courseID", courseID);
        model.addAttribute("lessonID", lessonID);
        return "user/lesson";
    }

    @RequestMapping(value = "/markLessonComplete/{lessonID}", method = RequestMethod.GET)
//    @ResponseBody
    public String markLessonComplete(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int lessonID) {
        //check logged in
        if (CookieServices.checkUserLoggedIn(request.getCookies())) {
            User user = UserDAO.getUserByUsername(CookieServices.getUserName(request.getCookies()));
            LessonDAO.insertLessonCompleted(user.getID(), lessonID, request);
        }

        Lesson lesson = LessonDAO.getLesson(lessonID);
        Mooc mooc = MoocDAO.getMooc(lesson.getMoocID());
        Course course = CourseDAO.getCourse(mooc.getCourseID());
        return "redirect:../learn/" + course.getID() + "/" + lessonID;
    }

    @RequestMapping(value = "/markLessonComplete/{lessonID}", method = RequestMethod.POST)
    @ResponseBody
    public String markLessonCompletePost(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int lessonID) {
        //check logged in
        if (CookieServices.checkUserLoggedIn(request.getCookies())) {
            User user = UserDAO.getUserByUsername(CookieServices.getUserName(request.getCookies()));
            LessonDAO.insertLessonCompleted(user.getID(), lessonID, request);
        }

        return "ok";
    }

    @RequestMapping(value = "/startAQuiz/{lessonID}", method = RequestMethod.GET)
    public String startAQuiz(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int lessonID) {
        //check logged in
        if (!CookieServices.checkUserLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:../../login";
        }

        User user = UserDAO.getUserByUsername(CookieServices.getUserName(request.getCookies()));
        Lesson lesson = LessonDAO.getLesson(lessonID);
        QuizResultDAO.insertQuizResult(new QuizResult(0, lessonID, user.getID(), new Date(), new Date((new Date()).getTime() + lesson.getTime() * 60000)));

        return "redirect:../learn/" + MoocDAO.getMooc(lesson.getMoocID()).getCourseID() + "/" + lessonID;
    }

    @RequestMapping(value = "/updateQuestionResult/{quizResultID}/{questionID}/{data}", method = RequestMethod.POST)
    @ResponseBody
    public String updateQuestionResult(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int quizResultID, @PathVariable int questionID, @PathVariable String data) {
        //check logged in
        if (!CookieServices.checkUserLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:../../login";
        }

        User user = UserDAO.getUserByUsername(CookieServices.getUserName(request.getCookies()));

        //check owner
        QuizResult quizResult = QuizResultDAO.getQuizResult(quizResultID);
        if (quizResult.getUserID() != user.getID()) {
            return "not owned";
        }

        if (quizResult.getEndTime().before(new Date())) {
            return "out of time!";
        }

        QuestionResultDAO.deleteQuestionResultOfQuestion(quizResultID, questionID);

        String[] answerIDs = data.split("_");
        for (String i : answerIDs) {
            try {
                int answerID = Integer.parseInt(i);
                QuestionResultDAO.insertQuestionResult(quizResultID, questionID, answerID);
            } catch (NumberFormatException e) {

            }
        }

        return "ok";
    }

    @RequestMapping(value = "/endAQuiz/{quizResultID}", method = RequestMethod.GET)
    public String endAQuiz(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int quizResultID) {
        //check logged in
        if (!CookieServices.checkUserLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:../login";
        }

        User user = UserDAO.getUserByUsername(CookieServices.getUserName(request.getCookies()));

        //check quizResult exist
        QuizResult quizResult = QuizResultDAO.getQuizResult(quizResultID);
        if (quizResult == null) {
            return "redirect:../";
        }

        //check owner
        if (quizResult.getUserID() != user.getID()) {
            return "redirect:../";
        }

        Lesson lesson = LessonDAO.getLesson(quizResult.getLessonID());
        Mooc mooc = MoocDAO.getMooc(lesson.getMoocID());

        //if quiz end yet
        if (quizResult.getEndTime().before(new Date())) {
            return "redirect:../learn/" + mooc.getCourseID() + "/" + lesson.getID();
        }

        //set endTime to current
        quizResult.setEndTime(new Date());
        QuizResultDAO.updateQuizResult(quizResult);

        int numberOfCorrectQuestion = QuizResultDAO.getQuizResultPoint(quizResultID);
        int numberOfQuestion = QuestionDAO.getNumberQuestionByLessonID(lesson.getID());
        if (numberOfCorrectQuestion * 100 >= numberOfQuestion * 80) {
            LessonDAO.insertLessonCompleted(user.getID(), lesson.getID(), request);
        }

        return "redirect:../learn/" + mooc.getCourseID() + "/" + lesson.getID();
    }

    @RequestMapping(value = "/course/{courseID}", method = RequestMethod.GET)
    public String course(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int courseID) {
        model.addAttribute("courseID", courseID);
        return "user/course";
    }

    @RequestMapping(value = "/allCourses", method = RequestMethod.GET)
    public String allCourses(HttpServletRequest request, HttpServletResponse response) {
        return "user/allCourses";
    }
}

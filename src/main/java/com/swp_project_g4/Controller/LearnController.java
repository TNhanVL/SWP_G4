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
@RequestMapping("/learn")
public class LearnController {

    @RequestMapping(value = "/{courseID}", method = RequestMethod.GET)
    public String lesson(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int courseID) {
        model.addAttribute("courseID", courseID);
        return "user/lesson";
    }

    @RequestMapping(value = "/{courseID}/{lessonID}", method = RequestMethod.GET)
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
        Chapter chapter = ChapterDAO.getChapter(lesson.getChapterID());
        Course course = CourseDAO.getCourse(chapter.getCourseID());
        return "redirect:../learn/" + course.getCourseID() + "/" + lessonID;
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
            return "redirect:/login";
        }

        User user = UserDAO.getUserByUsername(CookieServices.getUserName(request.getCookies()));
        Lesson lesson = LessonDAO.getLesson(lessonID);
        QuizResultDAO.insertQuizResult(new QuizResult(0, lessonID, user.getID(), new Date(), new Date((new Date()).getTime() + lesson.getTime() * 60000)));

        return "redirect:../learn/" + ChapterDAO.getChapter(lesson.getChapterID()).getCourseID() + "/" + lessonID;
    }

    @RequestMapping(value = "/updateChosenAnswer/{quizResultID}/{questionID}/{data}", method = RequestMethod.POST)
    @ResponseBody
    public String updateChosenAnswer(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int quizResultID, @PathVariable int questionID, @PathVariable String data) {
        //check logged in
        if (!CookieServices.checkUserLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:/login";
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

        ChosenAnswerDAO.deleteChosenAnswerOfQuestion(quizResultID, questionID);

        String[] answerIDs = data.split("_");
        for (String i : answerIDs) {
            try {
                int answerID = Integer.parseInt(i);
                ChosenAnswerDAO.insertChosenAnswer(quizResultID, questionID, answerID);
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
        Chapter chapter = ChapterDAO.getChapter(lesson.getChapterID());

        //if quiz end yet
        if (quizResult.getEndTime().before(new Date())) {
            return "redirect:../learn/" + chapter.getCourseID() + "/" + lesson.getLessonID();
        }

        //set endTime to current
        quizResult.setEndTime(new Date());
        QuizResultDAO.updateQuizResult(quizResult);

        int numberOfCorrectQuestion = QuizResultDAO.getQuizResultPoint(quizResultID);
        int numberOfQuestion = QuestionDAO.getNumberQuestionByLessonID(lesson.getLessonID());
        if (numberOfCorrectQuestion * 100 >= numberOfQuestion * 80) {
            LessonDAO.insertLessonCompleted(user.getID(), lesson.getLessonID(), request);
        }

        return "redirect:../learn/" + chapter.getCourseID() + "/" + lesson.getLessonID();
    }

}
package com.swp_project_g4.Controller;

import com.swp_project_g4.Database.*;
import com.swp_project_g4.Model.*;
import com.swp_project_g4.Repository.Repo;
import com.swp_project_g4.Service.CookieServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("/learn")
public class LearnController {

    @Autowired
    private Repo repo;

    @RequestMapping(value = "/{courseID}", method = RequestMethod.GET)
    public String lesson(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int courseID) {
        String username = CookieServices.getUserNameOfLearner(request.getCookies());

        //check learner logged in
        var learnerOptional = repo.getLearnerRepository().findByUsername(username);
        if (!learnerOptional.isPresent()) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:/login";
        }
        int learnerID = learnerOptional.get().getID();

        //check course exist
        var courseOptional = repo.getCourseRepository().findById(courseID);
        if (!courseOptional.isPresent()) {
            if (!learnerOptional.isPresent()) {
                request.getSession().setAttribute("error", "Not exist this course!");
                return "redirect:/";
            }
        }
        var course = courseOptional.get();

        //check courseProgress
        var courseProgressOptional = repo.getCourseProgressRepository().findByCourseIDAndLearnerID(courseID, learnerID);
        var courseProgress = courseProgressOptional.orElse(new CourseProgress(learnerID, courseID));
        if (!courseProgressOptional.isPresent()) {
            request.getSession().setAttribute("error", "You need to purchased this course first!");
            return "redirect:/";
        }
        if(!courseProgress.isEnrolled()){
            courseProgress.setEnrolled(true);
            repo.getCourseProgressRepository().save(courseProgress);
        }

        //Get last uncompleted lesson
        int lessonID = 0;
        for (var chapter : course.getChapters()) {
            //check chapterProgress
            var chapterProgressOptional = repo.getChapterProgressRepository().findByChapterIDAndCourseProgressID(chapter.getID(), courseProgress.getID());
            var chapterProgress = chapterProgressOptional.orElse(new ChapterProgress(chapter.getID(), courseProgress.getID()));
            if (!chapterProgressOptional.isPresent()) {
                chapterProgress = repo.getChapterProgressRepository().save(chapterProgress);
            }
            for (var lesson : chapter.getLessons()) {
                lessonID = lesson.getID();
                //check lessonProgress
                var lessonProgressOptional = repo.getLessonProgressRepository().findByLessonIDAndChapterProgressID(lessonID, chapterProgress.getID());
                var lessonProgress = lessonProgressOptional.orElse(new LessonProgress(lessonID, chapterProgress.getID()));
                if (!lessonProgressOptional.isPresent() || lessonProgress.isCompleted() == false) {
                    if (!lessonProgressOptional.isPresent()) {
                        lessonProgress = repo.getLessonProgressRepository().save(lessonProgress);
                    }
                    return "redirect:/learn/" + courseID + "/" + lessonID;
                }
            }
        }

        return "redirect:/learn/" + courseID + "/" + lessonID;
    }

    @RequestMapping(value = "/{courseID}/{lessonID}", method = RequestMethod.GET)
    public String lesson(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int courseID, @PathVariable int lessonID) {
        String username = CookieServices.getUserNameOfLearner(request.getCookies());

        //check learner logged in
        var learnerOptional = repo.getLearnerRepository().findByUsername(username);
        if (!learnerOptional.isPresent()) {
            request.getSession().setAttribute("error", "You must be logged in before enter this lesson!");
            return "redirect:/login";
        }
        var learner = learnerOptional.get();
        int learnerID = learner.getID();

        //check course exist
        var courseOptional = repo.getCourseRepository().findById(courseID);
        if (!courseOptional.isPresent()) {
            if (!learnerOptional.isPresent()) {
                request.getSession().setAttribute("error", "Not exist this course!");
                return "redirect:/";
            }
        }

        //check courseProgress
        var courseProgressOptional = repo.getCourseProgressRepository().findByCourseIDAndLearnerID(courseID, learnerID);
        var courseProgress = courseProgressOptional.orElse(new CourseProgress(learnerID, courseID));
        if (!courseProgressOptional.isPresent()) {
            request.getSession().setAttribute("error", "You need to purchased this course first!");
            return "redirect:/";
        }
        if(!courseProgress.isEnrolled()){
            courseProgress.setEnrolled(true);
            repo.getCourseProgressRepository().save(courseProgress);
        }

        //check exist lesson
        var lessonOptional = repo.getLessonRepository().findById(lessonID);
        if (!lessonOptional.isPresent()) {
            request.getSession().setAttribute("error", "Not exist this lesson!");
            return "redirect:/";
        }
        var lesson = lessonOptional.get();

        //check course include lesson
        var chapter = repo.getChapterRepository().findById(lesson.getChapterID()).get();
        var course = repo.getCourseRepository().findById(chapter.getCourseID()).get();
        if (course.getID() != courseID) {
            request.getSession().setAttribute("error", "Not exist this lesson in the course!");
            return "redirect:/";
        }

        //check chapterProgress
        var chapterProgressOptional = repo.getChapterProgressRepository().findByChapterIDAndCourseProgressID(chapter.getID(), courseProgress.getID());
        var chapterProgress = chapterProgressOptional.orElse(new ChapterProgress(chapter.getID(), courseProgress.getID()));
        if (!chapterProgressOptional.isPresent()) {
            chapterProgress = repo.getChapterProgressRepository().save(chapterProgress);
        }

        //check lessonProgress
        var lessonProgressOptional = repo.getLessonProgressRepository().findByLessonIDAndChapterProgressID(lessonID, chapterProgress.getID());
        var lessonProgress = lessonProgressOptional.orElse(new LessonProgress(lessonID, chapterProgress.getID()));
        if (!lessonProgressOptional.isPresent()) {
            lessonProgress = repo.getLessonProgressRepository().save(lessonProgress);
        }

        model.addAttribute("learner", learner);
        model.addAttribute("course", course);
        model.addAttribute("chapter", chapter);
        model.addAttribute("lesson", lesson);
        model.addAttribute("courseID", courseID);
        model.addAttribute("lessonID", lessonID);
        return "user/lesson";
    }

    @RequestMapping(value = "/markLessonComplete/{lessonID}", method = RequestMethod.GET)
//    @ResponseBody
    public String markLessonComplete(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int lessonID) {
        //check logged in
        if (CookieServices.checkLearnerLoggedIn(request.getCookies())) {
            Learner learner = LearnerDAO.getUserByUsername(CookieServices.getUserNameOfLearner(request.getCookies()));
            LessonDAO.insertLessonCompleted(learner.getID(), lessonID, request);
        }

        Lesson lesson = LessonDAO.getLesson(lessonID);
        Chapter chapter = ChapterDAO.getChapter(lesson.getChapterID());
        Course course = CourseDAO.getCourse(chapter.getCourseID());
        return "redirect:/learn/" + course.getID() + "/" + lessonID;
    }

    @RequestMapping(value = "/markLessonComplete/{lessonID}", method = RequestMethod.POST)
    @ResponseBody
    public String markLessonCompletePost(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int lessonID) {
        //check logged in
        if (CookieServices.checkLearnerLoggedIn(request.getCookies())) {
            Learner learner = LearnerDAO.getUserByUsername(CookieServices.getUserNameOfLearner(request.getCookies()));
            LessonDAO.insertLessonCompleted(learner.getID(), lessonID, request);
        }

        return "ok";
    }

    @RequestMapping(value = "/startAQuiz/{lessonID}", method = RequestMethod.GET)
    public String startAQuiz(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int lessonID) {
        //check logged in
        if (!CookieServices.checkLearnerLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:/login";
        }

        Learner learner = LearnerDAO.getUserByUsername(CookieServices.getUserNameOfLearner(request.getCookies()));
        Lesson lesson = LessonDAO.getLesson(lessonID);
//        QuizResultDAO.insertQuizResult(new QuizResult(0, lessonID, user.getID(), new Date(), new Date((new Date()).getTime() + lesson.getTime() * 60000)));

        return "redirect:/learn/" + ChapterDAO.getChapter(lesson.getChapterID()).getCourseID() + "/" + lessonID;
    }

    @RequestMapping(value = "/updateChosenAnswer/{quizResultID}/{questionID}/{data}", method = RequestMethod.POST)
    @ResponseBody
    public String updateChosenAnswer(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int quizResultID, @PathVariable int questionID, @PathVariable String data) {
        //check logged in
        if (!CookieServices.checkLearnerLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:/login";
        }

        Learner learner = LearnerDAO.getUserByUsername(CookieServices.getUserNameOfLearner(request.getCookies()));

        //check owner
        QuizResult quizResult = QuizResultDAO.getQuizResult(quizResultID);
//        if (quizResult.getUserID() != user.getID()) {
//            return "not owned";
//        }

        if (quizResult.getEndAt().before(new Date())) {
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
        if (!CookieServices.checkLearnerLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:/login";
        }

        Learner learner = LearnerDAO.getUserByUsername(CookieServices.getUserNameOfLearner(request.getCookies()));

        //check quizResult exist
        QuizResult quizResult = QuizResultDAO.getQuizResult(quizResultID);
        if (quizResult == null) {
            return "redirect:/";
        }

        //check owner
//        if (quizResult.getUserID() != user.getID()) {
//            return "redirect:/";
//        }

        Lesson lesson = LessonDAO.getLesson(quizResult.getLessonID());
        Chapter chapter = ChapterDAO.getChapter(lesson.getChapterID());

        //if quiz end yet
        if (quizResult.getEndAt().before(new Date())) {
            return "redirect:/learn/" + chapter.getCourseID() + "/" + lesson.getID();
        }

        //set end_at to current
        quizResult.setEndAt(new Date());
        QuizResultDAO.updateQuizResult(quizResult);

        int numberOfCorrectQuestion = QuizResultDAO.getQuizResultPoint(quizResultID);
        int numberOfQuestion = QuestionDAO.getNumberQuestionByLessonID(lesson.getID());
        if (numberOfCorrectQuestion * 100 >= numberOfQuestion * 80) {
            LessonDAO.insertLessonCompleted(learner.getID(), lesson.getID(), request);
        }

        return "redirect:/learn/" + chapter.getCourseID() + "/" + lesson.getID();
    }

}
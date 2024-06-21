package com.swp_project_g4.Controller;

import com.swp_project_g4.Database.*;
import com.swp_project_g4.Model.*;
import com.swp_project_g4.Repository.Repository;
import com.swp_project_g4.Service.CookieServices;
import com.swp_project_g4.Service.model.CourseProgressService;
import com.swp_project_g4.Service.model.LessonProgressService;
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
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/learn")
public class LearnController {

    @Autowired
    private Repository repository;
    @Autowired
    private LessonProgressService lessonProgressService;
    @Autowired
    private CourseProgressService courseProgressService;

    @RequestMapping(value = "/{courseID}", method = RequestMethod.GET)
    public String lesson(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int courseID) {
        String username = CookieServices.getUserNameOfLearner(request.getCookies());

        //check learner logged in
        var learnerOptional = repository.getLearnerRepository().findByUsername(username);
        if (!learnerOptional.isPresent()) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:/login";
        }
        int learnerID = learnerOptional.get().getID();

        //check course exist
        var courseOptional = repository.getCourseRepository().findById(courseID);
        if (!courseOptional.isPresent()) {
            if (!learnerOptional.isPresent()) {
                request.getSession().setAttribute("error", "Not exist this course!");
                return "redirect:/";
            }
        }
        var course = courseOptional.get();

        //check courseProgress
        var courseProgressOptional = repository.getCourseProgressRepository().findByCourseIDAndLearnerID(courseID, learnerID);
        if (!courseProgressOptional.isPresent()) {
            request.getSession().setAttribute("error", "You need to purchased this course first!");
            return "redirect:/";
        }
        var courseProgress = courseProgressOptional.get();
        if (!courseProgress.isEnrolled()) {
            courseProgressService.enroll(courseProgress);
        }

        //Get last uncompleted lesson
        int lessonID = 0;
        for (var chapter : course.getChapters()) {
            //check chapterProgress
            var chapterProgressOptional = repository.getChapterProgressRepository().findByChapterIDAndCourseProgressID(chapter.getID(), courseProgress.getID());
            var chapterProgress = chapterProgressOptional.orElse(new ChapterProgress(chapter.getID(), courseProgress.getID()));
            if (!chapterProgressOptional.isPresent()) {
                chapterProgress = repository.getChapterProgressRepository().save(chapterProgress);
            }
            for (var lesson : chapter.getLessons()) {
                lessonID = lesson.getID();
                //check lessonProgress
                var lessonProgressOptional = repository.getLessonProgressRepository().findByLessonIDAndChapterProgressID(lessonID, chapterProgress.getID());
                var lessonProgress = lessonProgressOptional.orElse(new LessonProgress(lessonID, chapterProgress.getID()));
                if (!lessonProgressOptional.isPresent() || !lessonProgress.isCompleted()) {
                    if (!lessonProgressOptional.isPresent()) {
                        lessonProgress = repository.getLessonProgressRepository().save(lessonProgress);
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
        var learnerOptional = repository.getLearnerRepository().findByUsername(username);
        if (!learnerOptional.isPresent()) {
            request.getSession().setAttribute("error", "You must be logged in before enter this lesson!");
            return "redirect:/login";
        }
        var learner = learnerOptional.get();
        int learnerID = learner.getID();

        //check course exist
        var courseOptional = repository.getCourseRepository().findById(courseID);
        if (!courseOptional.isPresent()) {
            if (!learnerOptional.isPresent()) {
                request.getSession().setAttribute("error", "Not exist this course!");
                return "redirect:/";
            }
        }

        //check courseProgress
        var courseProgressOptional = repository.getCourseProgressRepository().findByCourseIDAndLearnerID(courseID, learnerID);
        if (!courseProgressOptional.isPresent()) {
            request.getSession().setAttribute("error", "You need to purchased this course first!");
            return "redirect:/";
        }
        var courseProgress = courseProgressOptional.get();
        if (!courseProgress.isEnrolled()) {
            courseProgressService.enroll(courseProgress);
        }

        //check exist lesson
        var lessonOptional = repository.getLessonRepository().findById(lessonID);
        if (!lessonOptional.isPresent()) {
            request.getSession().setAttribute("error", "Not exist this lesson!");
            return "redirect:/";
        }
        var lesson = lessonOptional.get();

        //check course include lesson
        var chapter = repository.getChapterRepository().findById(lesson.getChapterID()).get();
        var course = repository.getCourseRepository().findById(chapter.getCourseID()).get();
        if (course.getID() != courseID) {
            request.getSession().setAttribute("error", "Not exist this lesson in the course!");
            return "redirect:/";
        }

        //check chapterProgress
        var chapterProgressOptional = repository.getChapterProgressRepository().findByChapterIDAndCourseProgressID(chapter.getID(), courseProgress.getID());
        var chapterProgress = chapterProgressOptional.orElse(new ChapterProgress(chapter.getID(), courseProgress.getID()));
        if (!chapterProgressOptional.isPresent()) {
            chapterProgress = repository.getChapterProgressRepository().save(chapterProgress);
        }

        //check lessonProgress
        var lessonProgressOptional = repository.getLessonProgressRepository().findByLessonIDAndChapterProgressID(lessonID, chapterProgress.getID());
        var lessonProgress = lessonProgressOptional.orElse(new LessonProgress(lessonID, chapterProgress.getID()));
        if (!lessonProgressOptional.isPresent()) {
            lessonProgress = repository.getLessonProgressRepository().save(lessonProgress);
        }

        Set<Integer> completedLessonIDs = new HashSet<>();
        for (var chapterProgress1 : courseProgress.getChapterProgresses()) {
            for (var lessonProgress1 : chapterProgress1.getLessonProgresses()) {
                if (lessonProgress1.isCompleted()) {
                    completedLessonIDs.add(lessonProgress1.getLessonID());
                }
            }
        }

        //if lesson type == 2 (quiz)
        if (lesson.getType() == 2) {
            var quizResults = repository.getQuizResultRepository().findByLessonIDAndLessonProgressID(lesson.getID(), lessonProgress.getID());
            if (!quizResults.isEmpty()) {
                model.addAttribute("quizResult", quizResults.get(quizResults.size() - 1));
            }
        }

        model.addAttribute("learner", learner);
        model.addAttribute("course", course);
        model.addAttribute("chapter", chapter);
        model.addAttribute("lesson", lesson);
        model.addAttribute("courseID", courseID);
        model.addAttribute("lessonID", lessonID);
        model.addAttribute("lessonProgressID", lessonProgress.getID());
        model.addAttribute("completedLessonIDs", completedLessonIDs);
        return "user/lesson";
    }

    @RequestMapping(value = "/markLessonComplete/{lessonID}", method = RequestMethod.GET)
//    @ResponseBody
    public String markLessonComplete(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int lessonID) {
        //check logged in
        if (CookieServices.checkLearnerLoggedIn(request.getCookies())) {
            Learner learner = LearnerDAO.getUserByUsername(CookieServices.getUserNameOfLearner(request.getCookies()));
//            LessonDAO.insertLessonCompleted(learner.getID(), lessonID, request);
            lessonProgressService.markLessonCompleted(learner.getID(), lessonID);
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
//            LessonDAO.insertLessonCompleted(learner.getID(), lessonID, request);
            lessonProgressService.markLessonCompleted(learner.getID(), lessonID);
        }

        return "ok";
    }

    @RequestMapping(value = "/startAQuiz/{lessonID}/{lessonProgressID}", method = RequestMethod.GET)
    public String startAQuiz(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int lessonID, @PathVariable int lessonProgressID) {
        //check logged in
        if (!CookieServices.checkLearnerLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:/login";
        }

        //check exist lesson
        var lessonOptional = repository.getLessonRepository().findById(lessonID);
        if (!lessonOptional.isPresent()) {
            request.getSession().setAttribute("error", "Not exist this lesson!");
            return "redirect:/";
        }
        var lesson = lessonOptional.get();

        //check lessonProgress
        var lessonProgressOptional = repository.getLessonProgressRepository().findById(lessonProgressID);
        if (!lessonProgressOptional.isPresent()) {
            request.getSession().setAttribute("error", "Not exist this lesson progress!");
            return "redirect:/";
        }


        QuizResult quizResult = new QuizResult(lessonID, lessonProgressID, lesson);
        repository.getQuizResultRepository().save(quizResult);
        return "redirect:/learn/" + lesson.getChapter().getCourseID() + "/" + lessonID;
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
        QuizResult quizResult = repository.getQuizResultRepository().findById(quizResultID).get();
        if (quizResult == null) {
            return "redirect:/";
        }

        Lesson lesson = LessonDAO.getLesson(quizResult.getLessonID());
        Chapter chapter = ChapterDAO.getChapter(lesson.getChapterID());

        //if quiz end yet
        if (quizResult.getEndAt().before(new Date())) {
            return "redirect:/learn/" + chapter.getCourseID() + "/" + lesson.getID();
        }

        //set end_at to current
        if (quizResult.getEndAt().after(new Date())) quizResult.setEndAt(new Date());
        quizResult.setFinished(true);
        repository.getQuizResultRepository().save(quizResult);

        int numberOfCorrectQuestion = QuizResultDAO.getQuizResultPoint(quizResultID);
        int numberOfQuestion = QuestionDAO.getNumberQuestionByLessonID(lesson.getID());
        if (numberOfCorrectQuestion * 100 >= numberOfQuestion * lesson.getPercentToPassed()) {
//            LessonDAO.insertLessonCompleted(learner.getID(), lesson.getID(), request);
            lessonProgressService.markLessonCompleted(learner.getID(), lesson.getID());
        }
        return "redirect:/learn/" + chapter.getCourseID() + "/" + lesson.getID();
    }

}
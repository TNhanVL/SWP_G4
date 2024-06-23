package com.swp_project_g4.Controller;

import com.swp_project_g4.Database.*;
import com.swp_project_g4.Model.*;
import com.swp_project_g4.Repository.ChosenAnswerRepository;
import com.swp_project_g4.Repository.Repository;
import com.swp_project_g4.Service.CookieServices;
import com.swp_project_g4.Service.model.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/learn")
public class LearnController {
    @Autowired
    private LearnerService learnerService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private LessonProgressService lessonProgressService;
    @Autowired
    private ChapterProgressService chapterProgressService;
    @Autowired
    private CourseProgressService courseProgressService;
    @Autowired
    private QuizResultService quizResultService;
    @Autowired
    private ChosenAnswerService chosenAnswerService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuizService quizService;

    @RequestMapping(value = "/{courseId}", method = RequestMethod.GET)
    public String lesson(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int courseId) {
        String username = CookieServices.getUserNameOfLearner(request.getCookies());

        //check learner logged in
        var learnerOptional = learnerService.findByUsername(username);
        if (!learnerOptional.isPresent()) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:/login";
        }
        int learnerId = learnerOptional.get().getID();

        //check course exist
        var courseOptional = courseService.findById(courseId);
        if (!courseOptional.isPresent()) {
            if (!learnerOptional.isPresent()) {
                request.getSession().setAttribute("error", "Not exist this course!");
                return "redirect:/";
            }
        }
        var course = courseOptional.get();

        //check courseProgress
        var courseProgressOptional = courseProgressService.findByCourseIdAndLearnerId(courseId, learnerId);
        if (!courseProgressOptional.isPresent()) {
            request.getSession().setAttribute("error", "You need to purchased this course first!");
            return "redirect:/";
        }
        var courseProgress = courseProgressOptional.get();
        if (!courseProgress.isEnrolled()) {
            courseProgressService.enroll(courseProgress);
        }

        //Get last uncompleted lesson
        int lessonId = 0;
        for (var chapter : course.getChapters()) {
            //check chapterProgress
            var chapterProgressOptional = chapterProgressService.findByChapterIdAndCourseProgressId(chapter.getID(), courseProgress.getID());
            var chapterProgress = chapterProgressOptional.orElse(new ChapterProgress(chapter.getID(), courseProgress.getID()));
            if (!chapterProgressOptional.isPresent()) {
                chapterProgress = chapterProgressService.save(chapterProgress);
            }
            for (var lesson : chapter.getLessons()) {
                lessonId = lesson.getID();
                //check lessonProgress
                var lessonProgressOptional = lessonProgressService.findByLessonIdAndChapterProgressId(lessonId, chapterProgress.getID());
                var lessonProgress = lessonProgressOptional.orElse(new LessonProgress(lessonId, chapterProgress.getID()));
                if (!lessonProgressOptional.isPresent() || !lessonProgress.isCompleted()) {
                    if (!lessonProgressOptional.isPresent()) {
                        lessonProgressService.save(lessonProgress);
                        return "redirect:/learn/" + courseId + "/" + lessonId;
                    }
                }
            }
        }

        return "redirect:/learn/" + courseId + "/" + lessonId;
    }

    @RequestMapping(value = "/{courseId}/{lessonId}", method = RequestMethod.GET)
    public String lesson(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int courseId, @PathVariable int lessonId) {
        String username = CookieServices.getUserNameOfLearner(request.getCookies());

        //check learner logged in
        var learnerOptional = learnerService.findByUsername(username);
        if (!learnerOptional.isPresent()) {
            request.getSession().setAttribute("error", "You must be logged in before enter this lesson!");
            return "redirect:/login";
        }
        var learner = learnerOptional.get();
        int learnerId = learner.getID();

        //check course exist
        var courseOptional = courseService.findById(courseId);
        if (!courseOptional.isPresent()) {
            if (!learnerOptional.isPresent()) {
                request.getSession().setAttribute("error", "Not exist this course!");
                return "redirect:/";
            }
        }

        //check courseProgress
        var courseProgressOptional = courseProgressService.findByCourseIdAndLearnerId(courseId, learnerId);
        if (!courseProgressOptional.isPresent()) {
            request.getSession().setAttribute("error", "You need to purchased this course first!");
            return "redirect:/";
        }
        var courseProgress = courseProgressOptional.get();
        if (!courseProgress.isEnrolled()) {
            courseProgressService.enroll(courseProgress);
        }

        //check exist lesson
        var lessonOptional = lessonService.findById(lessonId);
        if (!lessonOptional.isPresent()) {
            request.getSession().setAttribute("error", "Not exist this lesson!");
            return "redirect:/";
        }
        var lesson = lessonOptional.get();

        //check course include lesson
        var chapter = chapterService.findById(lesson.getChapterId()).get();
        var course = courseService.findById(chapter.getCourseId()).get();
        if (course.getID() != courseId) {
            request.getSession().setAttribute("error", "Not exist this lesson in the course!");
            return "redirect:/";
        }

        //check chapterProgress
        var chapterProgressOptional = chapterProgressService.findByChapterIdAndCourseProgressId(chapter.getID(), courseProgress.getID());
        var chapterProgress = chapterProgressOptional.orElse(new ChapterProgress(chapter.getID(), courseProgress.getID()));
        if (!chapterProgressOptional.isPresent()) {
            chapterProgress = chapterProgressService.save(chapterProgress);
        }

        //check lessonProgress
        var lessonProgressOptional = lessonProgressService.findByLessonIdAndChapterProgressId(lessonId, chapterProgress.getID());
        var lessonProgress = lessonProgressOptional.orElse(new LessonProgress(lessonId, chapterProgress.getID()));
        if (!lessonProgressOptional.isPresent()) {
            lessonProgress = lessonProgressService.save(lessonProgress);
        }

        Set<Integer> completedLessonIds = new HashSet<>();
        for (var chapterProgress1 : courseProgress.getChapterProgresses()) {
            for (var lessonProgress1 : chapterProgress1.getLessonProgresses()) {
                if (lessonProgress1.isCompleted()) {
                    completedLessonIds.add(lessonProgress1.getLessonId());
                }
            }
        }

        //if lesson type == 2 (quiz)
        if (lesson.getType() == 2) {
            var quiz = lesson.getQuiz();
            var quizResults = quizResultService.findAllByQuizIdAndLessonProgressID(quiz.getID(), lessonProgress.getID());
            if (!quizResults.isEmpty()) {
                var quizResult = quizResults.get(quizResults.size() - 1);
                ArrayList<Boolean> questionCorrects = new ArrayList<Boolean>();
                for (var question: quiz.getQuestions()) {
                    boolean correct = false;
                    if (chosenAnswerService.isQuestionCorrect(quizResult.getID(), question.getID())) correct = true;
                    questionCorrects.add(correct);
                    var answers = question.getAnswers();
                    model.addAttribute("answers_of_question_" + question.getID(), answers);
                    for (var answer: answers) {
                        if (chosenAnswerService.findByQuizResultIdAndAnswerId(quizResult.getID(), answer.getID()).isEmpty()) continue;
                        model.addAttribute("checked_" + answer.getID(), true);
                    }
                }
                model.addAttribute("quizResultTotalMark", quizResultService.calcTotalMarkByQuizResultId(quizResult.getID()));
                model.addAttribute("questionCorrects", questionCorrects);
                model.addAttribute("quizResult", quizResult);
            }

            model.addAttribute("numberOfQuestion", quiz.getQuestions().size());
            model.addAttribute("questions", quiz.getQuestions());
        }

        model.addAttribute("learner", learner);
        model.addAttribute("course", course);
        model.addAttribute("chapter", chapter);
        model.addAttribute("lesson", lesson);
        model.addAttribute("courseId", courseId);
        model.addAttribute("lessonId", lessonId);
        model.addAttribute("lessonProgressID", lessonProgress.getID());
        model.addAttribute("completedLessonIds", completedLessonIds);
        return "user/lesson";
    }

    @RequestMapping(value = "/markLessonComplete/{lessonId}", method = RequestMethod.GET)
//    @ResponseBody
    public String markLessonComplete(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int lessonId) {
        //check logged in
        if (CookieServices.checkLearnerLoggedIn(request.getCookies())) {
            Learner learner = LearnerDAO.getUserByUsername(CookieServices.getUserNameOfLearner(request.getCookies()));
//            LessonDAO.insertLessonCompleted(learner.getID(), lessonId, request);
            lessonProgressService.markLessonCompleted(learner.getID(), lessonId);
        }

        Lesson lesson = LessonDAO.getLesson(lessonId);
        Chapter chapter = ChapterDAO.getChapter(lesson.getChapterId());
        Course course = CourseDAO.getCourse(chapter.getCourseId());
        return "redirect:/learn/" + course.getID() + "/" + lessonId;
    }

    @RequestMapping(value = "/markLessonComplete/{lessonId}", method = RequestMethod.POST)
    @ResponseBody
    public String markLessonCompletePost(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int lessonId) {
        //check logged in
        if (CookieServices.checkLearnerLoggedIn(request.getCookies())) {
            Learner learner = LearnerDAO.getUserByUsername(CookieServices.getUserNameOfLearner(request.getCookies()));
//            LessonDAO.insertLessonCompleted(learner.getID(), lessonId, request);
            lessonProgressService.markLessonCompleted(learner.getID(), lessonId);
        }

        return "ok";
    }

    @RequestMapping(value = "/startAQuiz/{lessonId}/{lessonProgressID}", method = RequestMethod.GET)
    public String startAQuiz(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int lessonId, @PathVariable int lessonProgressID) {
        //check logged in
        if (!CookieServices.checkLearnerLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:/login";
        }

        //check exist lesson
        var lessonOptional = lessonService.findById(lessonId);
        if (!lessonOptional.isPresent()) {
            request.getSession().setAttribute("error", "Not exist this lesson!");
            return "redirect:/";
        }
        var lesson = lessonOptional.get();

        //check lessonProgress
        var lessonProgressOptional = lessonProgressService.findById(lessonProgressID);
        if (!lessonProgressOptional.isPresent()) {
            request.getSession().setAttribute("error", "Not exist this lesson progress!");
            return "redirect:/";
        }


        QuizResult quizResult = new QuizResult(lesson.getQuizId(), lessonProgressID, lesson);
        quizResultService.save(quizResult);
        return "redirect:/learn/" + lesson.getChapter().getCourseId() + "/" + lessonId;
    }

    @RequestMapping(value = "/updateChosenAnswer/{quizResultID}/{questionId}/{data}", method = RequestMethod.POST)
    @ResponseBody
    public String updateChosenAnswer(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int quizResultID, @PathVariable int questionId, @PathVariable String data) {
        //check logged in
        if (!CookieServices.checkLearnerLoggedIn(request.getCookies())) {
            request.getSession().setAttribute("error", "You need to log in to continue!");
            return "redirect:/login";
        }


        Learner learner = learnerService.findByUsername(CookieServices.getUserNameOfLearner(request.getCookies())).get();

        //check owner
        QuizResult quizResult = quizResultService.findById(quizResultID).get();
//        if (quizResult.getUserID() != user.getID()) {
//            return "not owned";
//        }

        if (quizResult.getEndAt().before(new Date())) {
            return "out of time!";
        }

        chosenAnswerService.deleteAllChosenAnswerWithQuizResultIdQuestionId(quizResultID, questionId);
//        ChosenAnswerDAO.deleteChosenAnswerOfQuestion(quizResultID, questionId);

        String[] answerIds = data.split("_");
        for (String i : answerIds) {
            try {
                int answerId = Integer.parseInt(i);
                chosenAnswerService.save(new ChosenAnswer(0, quizResultID, answerId, false));
//                ChosenAnswerDAO.insertChosenAnswer(quizResultID, answerId);
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

        Learner learner = learnerService.findByUsername(CookieServices.getUserNameOfLearner(request.getCookies())).get();

        //check quizResult exist
        QuizResult quizResult = quizResultService.findById(quizResultID).get();
        if (quizResult == null) {
            return "redirect:/";
        }

        Lesson lesson = lessonService.findById(quizResult.getQuiz().getLessonId()).get();
        Chapter chapter = chapterService.findById(lesson.getChapterId()).get();

        //if quiz end yet
        if (quizResult.getEndAt().before(new Date())) {
            return "redirect:/learn/" + chapter.getCourseId() + "/" + lesson.getID();
        }

        //set endAt to current
        if (quizResult.getEndAt().after(new Date())) quizResult.setEndAt(new Date());
        quizResult.setFinished(true);
        quizResultService.save(quizResult);

        var quiz = quizService.findByLessonId(lesson.getID()).get();
        int numberOfCorrectQuestion = quizResultService.calcTotalMarkByQuizResultId(quizResultID);
//        int numberOfCorrectQuestion = QuizResultDAO.getQuizResultPoint(quizResultID);
        int numberOfQuestion = questionService.findAllByQuizId(quiz.getID()).size();
        if (numberOfCorrectQuestion * 100 >= numberOfQuestion * lesson.getPercentToPassed()) {
//            LessonDAO.insertLessonCompleted(learner.getID(), lesson.getID(), request);
            lessonProgressService.markLessonCompleted(learner.getID(), lesson.getID());
        }
        return "redirect:/learn/" + chapter.getCourseId() + "/" + lesson.getID();
    }

}
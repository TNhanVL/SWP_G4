package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.Lesson;
import com.swp_project_g4.Model.Quiz;
import com.swp_project_g4.Model.QuizResult;
import com.swp_project_g4.Repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    private LessonProgressService lessonProgressService;
    @Autowired
    private QuizResultService quizResultService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private QuizRepository quizRepository;

    public Optional<Quiz> getById(int quizId) {
        return quizRepository.findByID(quizId);
    }

    public Optional<Quiz> getByLessonId(int lessonId) {
        return quizRepository.findByLessonId(lessonId);
    }

    public void endAQuiz(QuizResult quizResult) {
        if (quizResult.isFinished()) return;

        int learnerId = quizResult.getLessonProgress().getChapterProgress().getCourseProgress().getLearnerId();
        Lesson lesson = lessonService.getById(quizResult.getQuiz().getLessonId()).get();

        //set endAt to current
        if (quizResult.getEndAt().after(new Date())) quizResult.setEndAt(new Date());
        quizResult.setFinished(true);
        quizResultService.save(quizResult);

        int numberOfCorrectQuestion = quizResultService.calcTotalMarkByQuizResultId(quizResult.getID());
        int numberOfQuestion = questionService.getAllByQuizId(lesson.getQuiz().getID()).size();
        if (numberOfCorrectQuestion * 100 >= numberOfQuestion * lesson.getPercentToPassed()) {
            lessonProgressService.markLessonCompleted(learnerId, lesson.getID());
        }
    }

}

package com.swp_project_g4.Service.model;

import com.swp_project_g4.Database.*;
import com.swp_project_g4.Model.*;
import com.swp_project_g4.Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    public void endAQuiz(QuizResult quizResult) {
        if (quizResult.isFinished()) return;

        int learnerId = quizResult.getLessonProgress().getChapterProgress().getCourseProgress().getLearnerId();
        Lesson lesson = lessonService.getById(quizResult.getLessonId()).get();

        //set endAt to current
        if (quizResult.getEndAt().after(new Date())) quizResult.setEndAt(new Date());
        quizResult.setFinished(true);
        quizResultService.save(quizResult);

        int numberOfCorrectQuestion = quizResultService.calcTotalMarkByQuizResultId(quizResult.getID());
        int numberOfQuestion = questionService.getAllByLessonId(lesson.getID()).size();
        if (numberOfCorrectQuestion * 100 >= numberOfQuestion * lesson.getPercentToPassed()) {
            lessonProgressService.markLessonCompleted(learnerId, lesson.getID());
        }
    }

}

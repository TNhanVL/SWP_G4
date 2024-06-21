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
    private Repository repository;
    @Autowired
    private LessonProgressService lessonProgressService;

    public void endAQuiz(QuizResult quizResult) {
        if (quizResult.isFinished()) return;

        int learnerId = quizResult.getLessonProgress().getChapterProgress().getCourseProgress().getLearnerId();
        Lesson lesson = repository.getLessonRepository().findById(quizResult.getLessonId()).get();

        //set endAt to current
        if (quizResult.getEndAt().after(new Date())) quizResult.setEndAt(new Date());
        quizResult.setFinished(true);
        repository.getQuizResultRepository().save(quizResult);

        int numberOfCorrectQuestion = QuizResultDAO.getQuizResultPoint(quizResult.getID());
        int numberOfQuestion = QuestionDAO.getNumberQuestionByLessonId(lesson.getID());
        if (numberOfCorrectQuestion * 100 >= numberOfQuestion * lesson.getPercentToPassed()) {
            lessonProgressService.markLessonCompleted(learnerId, lesson.getID());
        }
    }

}

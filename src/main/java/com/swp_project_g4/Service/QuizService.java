package com.swp_project_g4.Service;

import com.swp_project_g4.Database.*;
import com.swp_project_g4.Model.*;
import com.swp_project_g4.Repository.CourseProgressRepository;
import com.swp_project_g4.Repository.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class QuizService {
    @Autowired
    private Repo repo;
    @Autowired
    private LessonProgressService lessonProgressService;

    public void endAQuiz(QuizResult quizResult) {
        if (quizResult.isFinished()) return;

        int learnerID = quizResult.getLessonProgress().getChapterProgress().getCourseProgress().getLearnerID();
        Lesson lesson = repo.getLessonRepository().findById(quizResult.getLessonID()).get();

        //set end_at to current
        quizResult.setEndAt(new Date());
        quizResult.setFinished(true);
        repo.getQuizResultRepository().save(quizResult);

        int numberOfCorrectQuestion = QuizResultDAO.getQuizResultPoint(quizResult.getID());
        int numberOfQuestion = QuestionDAO.getNumberQuestionByLessonID(lesson.getID());
        if (numberOfCorrectQuestion * 100 >= numberOfQuestion * lesson.getPercentToPassed()) {
            lessonProgressService.markLessonCompleted(learnerID, lesson.getID());
        }
    }

}

package com.swp_project_g4.Service.model;

import com.swp_project_g4.Database.QuestionDAO;
import com.swp_project_g4.Database.QuizResultDAO;
import com.swp_project_g4.Model.Lesson;
import com.swp_project_g4.Model.QuizResult;
import com.swp_project_g4.Repository.QuizResultRepository;
import com.swp_project_g4.Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class QuizResultService {
    @Autowired
    QuizResultRepository quizResultRepository;

    public Optional<QuizResult> getById(int quizResultId) {
        return quizResultRepository.findById(quizResultId);
    }

    public List<QuizResult> getAllByLessonIdAndLessonProgressID(int lessonId, int lessonProgressId) {
        return quizResultRepository.findAllByLessonIdAndLessonProgressID(lessonId, lessonProgressId);
    }

    public QuizResult save(QuizResult quizResult) {
        return quizResultRepository.save(quizResult);
    }
}

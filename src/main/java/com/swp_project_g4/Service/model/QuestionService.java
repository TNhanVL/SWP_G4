package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.Lesson;
import com.swp_project_g4.Model.Question;
import com.swp_project_g4.Repository.LessonRepository;
import com.swp_project_g4.Repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public Optional<Question> findById(int questionId) {
        return questionRepository.findById(questionId);
    }

    public List<Question> findAllByQuizId(int quizId) {
        return questionRepository.findAllByQuizId(quizId);
    }

    public Question save(Question question) {
        return questionRepository.save(question);
    }

    public void deleteById(int questionId) {
        questionRepository.deleteById(questionId);
    }
}

package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.Answer;
import com.swp_project_g4.Repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {
    @Autowired AnswerRepository answerRepository;

    public Optional<Answer> getById(int answerId) {
        return answerRepository.findById(answerId);
    }

    public List<Answer> getAllByQuestionId(int questionId) {
        return answerRepository.findAllByQuestionId(questionId);
    }

    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }

    public void deleteById(int answerId) {
        answerRepository.deleteById(answerId);
    }
}

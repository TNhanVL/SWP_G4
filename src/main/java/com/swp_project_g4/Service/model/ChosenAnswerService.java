package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.ChosenAnswer;
import com.swp_project_g4.Repository.AnswerRepository;
import com.swp_project_g4.Repository.ChosenAnswerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class ChosenAnswerService {
    @Autowired
    private ChosenAnswerRepository chosenAnswerRepository;
    @Autowired
    private AnswerService answerService;

    public Optional<ChosenAnswer> findByQuizResultIdAndAnswerId(int quizResultId, int answerId) {
        return chosenAnswerRepository.findByQuizResultIDAndAnswerId(quizResultId, answerId);
    }

    public List<ChosenAnswer> findAllByQuizResultId(int quizResultId) {
        return chosenAnswerRepository.findAllByQuizResultID(quizResultId);
    }

    public List<ChosenAnswer> findAllByQuizResultIdAndQuestionId(int quizResultId, int questionId) {
        var answers = answerService.findAllByQuestionId(questionId);
        List<ChosenAnswer> chosenAnswers = new ArrayList<>();
        for (var answer: answers) {
            var chosenAnswerOptional = findByQuizResultIdAndAnswerId(quizResultId, answer.getID());
            if (chosenAnswerOptional.isPresent()) chosenAnswers.add(chosenAnswerOptional.get());
        }
        return chosenAnswers;
    }

    public boolean isQuestionCorrect(int quizResultId, int questionId) {
        var answers = answerService.findAllByQuestionId(questionId);
        var chosenAnswers = findAllByQuizResultIdAndQuestionId(quizResultId, questionId);
        Set<Integer> s = new HashSet<>();
        for (var chosenAnswer: chosenAnswers) {
            s.add(chosenAnswer.getAnswerId());
        }
        for (var answer: answers) {
            if (!answer.isCorrect()) continue;
            if (!s.contains(answer.getID())) return false;
            s.remove(answer.getID());
        }
        if (!s.isEmpty()) return false;
        return true;
    }

    public void deleteAllChosenAnswerWithQuizResultIdQuestionId(int quizResultId, int questionId) {
        var answers = answerService.findAllByQuestionId(questionId);
        for(var answer: answers) {
            chosenAnswerRepository.deleteAllByQuizResultIDAndAnswerId(quizResultId, answer.getID());
        }
    }

    public ChosenAnswer save(ChosenAnswer chosenAnswer) {
        return chosenAnswerRepository.save(chosenAnswer);
    }
}

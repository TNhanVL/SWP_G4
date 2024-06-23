package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.QuizResult;
import com.swp_project_g4.Repository.QuizResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizResultService {
    @Autowired
    private QuizResultRepository quizResultRepository;
    @Autowired
    private ChosenAnswerService chosenAnswerService;
    @Autowired
    private QuestionService questionService;

    public Optional<QuizResult> findById(int quizResultId) {
        return quizResultRepository.findById(quizResultId);
    }

    public List<QuizResult> getAllByQuizIdAndLessonProgressID(int quizId, int lessonProgressId) {
        return quizResultRepository.findAllByQuizIdAndLessonProgressID(quizId, lessonProgressId);
    }

    public QuizResult save(QuizResult quizResult) {
        return quizResultRepository.save(quizResult);
    }

    public int calcTotalMarkByQuizResultId(int quizResultId) {
        var quizResult = findById(quizResultId).get();
        var questions = quizResult.getQuiz().getQuestions();
        int totalMark = 0;

        for (var question: questions) {
            totalMark += chosenAnswerService.isQuestionCorrect(quizResultId, question.getID()) ? 1 : 0;
        }

        return totalMark;
    }
}

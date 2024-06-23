package com.swp_project_g4.RestController;

import com.swp_project_g4.Model.Question;
import com.swp_project_g4.Repository.Repository;
import com.swp_project_g4.Service.model.QuestionService;
import com.swp_project_g4.Service.model.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:8080/", "http://localhost:3000/"}, allowCredentials = "true")
@RestController
@RequestMapping("api/question")
public class QuestionRestController {
    @Autowired
    private QuizService quizService;
    @Autowired
    private QuestionService questionService;

    @PostMapping("/findByQuestionId")
    public Question getByQuestionId(@RequestBody Map<String, Integer> data) {
        try {
            return questionService.findById(data.get("questionId")).get();
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/findByLessonId")
    public List<Question> getByLessonId(@RequestBody Map<String, Integer> data) {
        try {
            return questionService.findAllByQuizId(data.get("quizId"));
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/create")
    public Question create(@RequestBody Map<String, Integer> data) {
        try {
            int questionSize = quizService.findById(data.get("quizId")).get().getQuestions().size();
            Question question = new Question();
            question.setQuizId(data.get("quizId"));
            question.setIndex(questionSize + 1);
            question = questionService.save(question);
            return question;
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody Map<String, Integer> data) {
        try {
            questionService.deleteById(data.get("questionId"));
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    @PostMapping("/update")
    public Question update(@RequestBody Question ques) {
        try {
            Question question = questionService.findById(ques.getID()).get();
            question.setContent(ques.getContent());
            question.setIndex(ques.getIndex());
            question.setType(ques.getType());
            question.setPoint(ques.getPoint());
            question = questionService.save(question);
            return question;
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/reIndexs")
    public boolean update(@RequestBody Map<String, Integer> data) {
        try {
            int size = data.get("size");
            int lesson = data.get("lessonId");
            for (int i = 0; i < size; i++) {
                int id = data.get("id_" + i);
                int index = data.get("index_" + i);
                var question = questionService.findById(id).get();
                question.setIndex(index);
                questionService.save(question);
            }
            return true;
        } catch (Exception e) {

        }
        return false;
    }

}

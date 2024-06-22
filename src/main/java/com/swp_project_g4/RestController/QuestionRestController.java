package com.swp_project_g4.RestController;

import com.swp_project_g4.Model.Question;
import com.swp_project_g4.Repository.Repository;
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
    private Repository repository;
    @Autowired
    private QuizService quizService;

    @PostMapping("/getByQuestionId")
    public Question getByQuestionId(@RequestBody Map<String, Integer> data) {
        try {
            return repository.getQuestionRepository().findById(data.get("questionId")).get();
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/getByLessonId")
    public List<Question> getByLessonId(@RequestBody Map<String, Integer> data) {
        try {
            return repository.getQuestionRepository().findAllByQuizId(data.get("quizId"));
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/create")
    public Question create(@RequestBody Map<String, Integer> data) {
        try {
            int questionSize = quizService.getById(data.get("quizId")).get().getQuestions().size();
            Question question = new Question();
            question.setQuizId(data.get("quizId"));
            question.setIndex(questionSize + 1);
            question = repository.getQuestionRepository().save(question);
            return question;
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody Map<String, Integer> data) {
        try {
            repository.getQuestionRepository().deleteById(data.get("questionId"));
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    @PostMapping("/update")
    public Question update(@RequestBody Question ques) {
        try {
            Question question = repository.getQuestionRepository().findById(ques.getID()).get();
            question.setContent(ques.getContent());
            question.setIndex(ques.getIndex());
            question.setType(ques.getType());
            question.setPoint(ques.getPoint());
            question = repository.getQuestionRepository().save(question);
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
                var question = repository.getQuestionRepository().findById(id).get();
                question.setIndex(index);
                repository.getQuestionRepository().save(question);
            }
            return true;
        } catch (Exception e) {

        }
        return false;
    }

}

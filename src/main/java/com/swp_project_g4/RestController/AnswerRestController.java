package com.swp_project_g4.RestController;

import com.swp_project_g4.Model.Answer;
import com.swp_project_g4.Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:8080/", "http://localhost:3000/"}, allowCredentials = "true")
@RestController
@RequestMapping("api/answer")
public class AnswerRestController {
    @Autowired
    private Repository repository;

    @PostMapping("/getByAnswerID")
    public Answer getByAnswerID(@RequestBody Map<String, Integer> data) {
        try {
            return repository.getAnswerRepository().findById(data.get("answerID")).get();
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/getByQuestionID")
    public List<Answer> getByQuestionID(@RequestBody Map<String, Integer> data) {
        try {
            return repository.getAnswerRepository().findByQuestionID(data.get("questionID"));
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/create")
    public Answer create(@RequestBody Map<String, Integer> data) {
        try {
            int answerSize = repository.getQuestionRepository().findById(data.get("questionID")).get().getAnswers().size();
            Answer answer = new Answer();
            answer.setQuestionID(data.get("questionID"));
            answer = repository.getAnswerRepository().save(answer);
            return answer;
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody Map<String, Integer> data) {
        try {
            repository.getAnswerRepository().deleteById(data.get("answerID"));
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    @PostMapping("/update")
    public Answer update(@RequestBody Answer answer1) {
        try {
            Answer answer = repository.getAnswerRepository().findById(answer1.getID()).get();
            answer.setContent(answer1.getContent());
            answer.setCorrect(answer1.isCorrect());
            answer = repository.getAnswerRepository().save(answer);
            return answer;
        } catch (Exception e) {

        }
        return null;
    }

}

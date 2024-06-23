package com.swp_project_g4.RestController;

import com.swp_project_g4.Model.Answer;
import com.swp_project_g4.Repository.AnswerRepository;
import com.swp_project_g4.Repository.Repository;
import com.swp_project_g4.Service.model.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:8080/", "http://localhost:3000/"}, allowCredentials = "true")
@RestController
@RequestMapping("api/answer")
public class AnswerRestController {
    @Autowired
    private AnswerService answerService;

    @PostMapping("/findByAnswerID")
    public Answer getByAnswerID(@RequestBody Map<String, Integer> data) {
        try {
            return answerService.findById(data.get("answerId")).get();
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/findByQuestionId")
    public List<Answer> getByQuestionId(@RequestBody Map<String, Integer> data) {
        try {
            return answerService.getAllByQuestionId(data.get("questionId"));
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/create")
    public Answer create(@RequestBody Map<String, Integer> data) {
        try {
            int answerSize = answerService.getAllByQuestionId(data.get("questionId")).size();
            Answer answer = new Answer();
            answer.setQuestionId(data.get("questionId"));
            answer = answerService.save(answer);
            return answer;
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody Map<String, Integer> data) {
        try {
            answerService.deleteById(data.get("answerId"));
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    @PostMapping("/update")
    public Answer update(@RequestBody Answer answer1) {
        try {
            Answer answer = answerService.findById(answer1.getID()).get();
            answer.setContent(answer1.getContent());
            answer.setCorrect(answer1.isCorrect());
            answer = answerService.save(answer);
            return answer;
        } catch (Exception e) {

        }
        return null;
    }

}

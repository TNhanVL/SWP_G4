package com.swp_project_g4.RestController;

import com.swp_project_g4.Model.Lesson;
import com.swp_project_g4.Model.Question;
import com.swp_project_g4.Repository.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:8080/", "http://localhost:3000/"}, allowCredentials = "true")
@RestController
@RequestMapping("api/question")
public class QuestionRestController {
    @Autowired
    private Repo repo;

    @PostMapping("/getByQuestionID")
    public Question getByQuestionID(@RequestBody Map<String, Integer> data) {
        try {
            return repo.getQuestionRepository().findById(data.get("questionID")).get();
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/getByLessonID")
    public List<Question> getByLessonID(@RequestBody Map<String, Integer> data) {
        try {
            return repo.getQuestionRepository().findByLessonID(data.get("lessonID"));
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/create")
    public Question create(@RequestBody Map<String, Integer> data) {
        try {
            int questionSize = repo.getLessonRepository().findById(data.get("lessonID")).get().getQuestions().size();
            Question question = new Question();
            question.setLessonID(data.get("lessonID"));
            question.setIndex(questionSize + 1);
            question = repo.getQuestionRepository().save(question);
            return question;
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody Map<String, Integer> data) {
        try {
            repo.getQuestionRepository().deleteById(data.get("questionID"));
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    @PostMapping("/update")
    public Question update(@RequestBody Question ques) {
        try {
            Question question = repo.getQuestionRepository().findById(ques.getID()).get();
            question.setContent(ques.getContent());
            question.setIndex(ques.getIndex());
            question.setType(ques.getType());
            question.setPoint(ques.getPoint());
            question = repo.getQuestionRepository().save(question);
            return question;
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/reIndexs")
    public boolean update(@RequestBody Map<String, Integer> data) {
        try {
            int size = data.get("size");
            int lesson = data.get("lessonID");
            for (int i = 0; i < size; i++) {
                int id = data.get("id_" + i);
                int index = data.get("index_" + i);
                var question = repo.getQuestionRepository().findById(id).get();
                question.setIndex(index);
                repo.getQuestionRepository().save(question);
            }
            return true;
        } catch (Exception e) {

        }
        return false;
    }

}

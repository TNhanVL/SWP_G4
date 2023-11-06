package com.swp_project_g4.RestController;

import com.swp_project_g4.Model.Question;
import com.swp_project_g4.Repository.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/question")
public class QuestionRestController {
    @Autowired
    private Repo repo;

    @PostMapping("/getByLessonID")
    public List<Question> getByLessonID(@RequestBody Map<String, Integer> data) {
        try {
            return repo.getQuestionRepository().findByLessonID(data.get("lessonID")).get();
        } catch (Exception e) {

        }
        return null;
    }

    // uhhh ko dung' toi'
    @PostMapping("/getByChapterID")
    public ArrayList<Question> getByChapterID(@RequestBody Map<String, Integer> data) {
        try {
            var questions = new ArrayList<Question>();
            var lesson = repo.getChapterRepository().findById(data.get("chapterID")).get().getLessons();
            lesson.forEach(l -> {
                questions.addAll(repo.getQuestionRepository().findByLessonID(l.getID()).get());
            });

            return questions;
        } catch (Exception e) {

        }
        return new ArrayList<>();
    }

    @PostMapping("/create")
    public Question create(@RequestBody Map<String, Integer> data) {
        try {
            int questionSize = repo.getChapterRepository().findById(data.get("chapterID")).get().getLessons().size();
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
            question.setLesson(ques.getLesson());
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

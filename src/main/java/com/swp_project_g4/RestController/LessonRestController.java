package com.swp_project_g4.RestController;

import com.swp_project_g4.Model.Lesson;
import com.swp_project_g4.Repository.Repo;
import com.swp_project_g4.Service.model.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:8080/", "http://localhost:3000/"}, allowCredentials = "true")
@RestController
@RequestMapping("api/lesson")
public class LessonRestController {
    @Autowired
    private Repo repo;
    @Autowired
    private LessonService lessonService;

    @PostMapping("/getByLessonID")
    public Lesson getByLessonID(@RequestBody Map<String, Integer> data) {
        try {
            return repo.getLessonRepository().findById(data.get("lessonID")).get();
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/getByChapterID")
    public List<Lesson> getByChapterID(@RequestBody Map<String, Integer> data) {
        try {
            return repo.getChapterRepository().findById(data.get("chapterID")).get().getLessons();
        } catch (Exception e) {

        }
        return new ArrayList<>();
    }

    @PostMapping("/create")
    public Lesson create(@RequestBody Map<String, Integer> data) {
        try {
            int lessonSize = repo.getChapterRepository().findById(data.get("chapterID")).get().getLessons().size();
            Lesson lesson = new Lesson();
            lesson.setChapterID(data.get("chapterID"));
            lesson.setIndex(lessonSize + 1);
            lesson = repo.getLessonRepository().save(lesson);
            return lesson;
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody Map<String, Integer> data) {
        try {
            var lesson = repo.getLessonRepository().findById(data.get("lessonID")).get();
            repo.getLessonRepository().deleteById(lesson.getID());
            lessonService.reIndexAllLessonByChapterID(lesson.getChapterID());
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    @PostMapping("/update")
    public Lesson update(@RequestBody Lesson lesson1) {
        try {
            Lesson lesson = repo.getLessonRepository().findById(lesson1.getID()).get();
            lesson.setName(lesson1.getName());
            lesson.setDescription(lesson1.getDescription());
            lesson.setIndex(lesson1.getIndex());
            lesson.setPercentToPassed(lesson1.getPercentToPassed());
            lesson.setMustBeCompleted(lesson1.isMustBeCompleted());
            lesson = repo.getLessonRepository().save(lesson);
            return lesson;
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/reIndexs")
    public boolean update(@RequestBody Map<String, Integer> data) {
        try {
            int size = data.get("size");
            int chapterID = data.get("chapterID");
            for (int i = 0; i < size; i++) {
                int id = data.get("id_" + i);
                int index = data.get("index_" + i);
                var lesson = repo.getLessonRepository().findById(id).get();
                lesson.setIndex(index);
                repo.getLessonRepository().save(lesson);
            }
            return true;
        } catch (Exception e) {

        }
        return false;
    }
}

package com.swp_project_g4.RestController;

import com.swp_project_g4.Model.Lesson;
import com.swp_project_g4.Repository.Repository;
import com.swp_project_g4.Service.model.ChapterService;
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
    private ChapterService chapterService;
    @Autowired
    private LessonService lessonService;

    @PostMapping("/getByLessonId")
    public Lesson getByLessonId(@RequestBody Map<String, Integer> data) {
        try {
            return lessonService.findById(data.get("lessonId")).get();
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/getByChapterId")
    public List<Lesson> getByChapterId(@RequestBody Map<String, Integer> data) {
        try {
            return chapterService.findById(data.get("chapterId")).get().getLessons();
        } catch (Exception e) {

        }
        return new ArrayList<>();
    }

    @PostMapping("/create")
    public Lesson create(@RequestBody Map<String, Integer> data) {
        try {
            int lessonSize = chapterService.findById(data.get("chapterId")).get().getLessons().size();
            Lesson lesson = new Lesson();
            lesson.setChapterId(data.get("chapterId"));
            lesson.setIndex(lessonSize + 1);
            lesson = lessonService.save(lesson);
            return lesson;
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody Map<String, Integer> data) {
        try {
            var lesson = lessonService.findById(data.get("lessonId")).get();
            lessonService.deleteById(lesson.getID());
            chapterService.reIndexAllLessonByChapterId(lesson.getChapterId());
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    @PostMapping("/update")
    public Lesson update(@RequestBody Lesson lesson1) {
        try {
            Lesson lesson = lessonService.findById(lesson1.getID()).get();
            lesson.setName(lesson1.getName());
            lesson.setDescription(lesson1.getDescription());
            lesson.setIndex(lesson1.getIndex());
            lesson.setPercentToPassed(lesson1.getPercentToPassed());
            lesson.setMustBeCompleted(lesson1.isMustBeCompleted());
            lesson.setType(lesson1.getType());
            lesson.setTime(lesson1.getTime());
            lesson.setContent(lesson1.getContent());
            lesson = lessonService.save(lesson);
            return lesson;
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/reIndexs")
    public boolean update(@RequestBody Map<String, Integer> data) {
        try {
            int size = data.get("size");
            int chapterId = data.get("chapterId");
            for (int i = 0; i < size; i++) {
                int id = data.get("id_" + i);
                int index = data.get("index_" + i);
                var lesson = lessonService.findById(id).get();
                lesson.setIndex(index);
                lessonService.save(lesson);
            }
            return true;
        } catch (Exception e) {

        }
        return false;
    }
}

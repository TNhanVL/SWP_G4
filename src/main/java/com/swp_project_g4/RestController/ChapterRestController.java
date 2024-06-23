package com.swp_project_g4.RestController;

import com.swp_project_g4.Model.Chapter;
import com.swp_project_g4.Repository.Repository;
import com.swp_project_g4.Service.model.ChapterService;
import com.swp_project_g4.Service.model.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:8080/", "http://localhost:3000/"}, allowCredentials = "true")
@RestController
@RequestMapping("api/chapter")
public class ChapterRestController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private ChapterService chapterService;

    @PostMapping("/getByChapterId")
    public Chapter getByChapterId(@RequestBody Map<String, Integer> data) {
        try {
            return chapterService.getById(data.get("chapterId")).get();
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/getByCourseId")
    public List<Chapter> getByCourseId(@RequestBody Map<String, Integer> data) {
        try {
            return courseService.getById(data.get("courseId")).get().getChapters();
        } catch (Exception e) {

        }
        return new ArrayList<>();
    }

    @PostMapping("/create")
    public Chapter create(@RequestBody Map<String, Integer> data) {
        try {
            int chapterSize = courseService.getById(data.get("courseId")).get().getChapters().size();
            Chapter chapter = new Chapter();
            chapter.setCourseId(data.get("courseId"));
            chapter.setIndex(chapterSize + 1);
            chapter = chapterService.save(chapter);
            return chapter;
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody Map<String, Integer> data) {
        try {
            var chapter = chapterService.getById(data.get("chapterId")).get();
            chapterService.deleteById(chapter.getID());
            courseService.reIndexAllChapterByCourseId(chapter.getCourseId());
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    @PostMapping("/update")
    public Chapter update(@RequestBody Chapter chapter1) {
        try {
            Chapter chapter = chapterService.getById(chapter1.getID()).get();
            chapter.setName(chapter1.getName());
            chapter.setDescription(chapter1.getDescription());
            chapter.setIndex(chapter1.getIndex());
            chapter = chapterService.save(chapter);
            return chapter;
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/reIndexs")
    public boolean update(@RequestBody Map<String, Integer> data) {
        try {
            int size = data.get("size");
            int courseId = data.get("courseId");
            for (int i = 0; i < size; i++) {
                int id = data.get("id_" + i);
                int index = data.get("index_" + i);
                var chapter = chapterService.getById(id).get();
                chapter.setIndex(index);
                chapterService.save(chapter);
            }
            return true;
        } catch (Exception e) {

        }
        return false;
    }
}

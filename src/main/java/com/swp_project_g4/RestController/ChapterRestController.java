package com.swp_project_g4.RestController;

import com.swp_project_g4.Model.Chapter;
import com.swp_project_g4.Model.Course;
import com.swp_project_g4.Repository.Repo;
import com.swp_project_g4.Service.model.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:8080/", "http://localhost:3000/"}, allowCredentials = "true")
@RestController
@RequestMapping("api/chapter")
public class ChapterRestController {
    @Autowired
    private Repo repo;
    @Autowired
    private ChapterService chapterService;

    @PostMapping("/getByChapterID")
    public Chapter getByChapterID(@RequestBody Map<String, Integer> data) {
        try {
            return repo.getChapterRepository().findById(data.get("chapterID")).get();
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/getByCourseID")
    public List<Chapter> getByCourseID(@RequestBody Map<String, Integer> data) {
        try {
            return repo.getCourseRepository().findById(data.get("courseID")).get().getChapters();
        } catch (Exception e) {

        }
        return new ArrayList<>();
    }

    @PostMapping("/create")
    public Chapter create(@RequestBody Map<String, Integer> data) {
        try {
            int chapterSize = repo.getCourseRepository().findById(data.get("courseID")).get().getChapters().size();
            Chapter chapter = new Chapter();
            chapter.setCourseID(data.get("courseID"));
            chapter.setIndex(chapterSize + 1);
            chapter = repo.getChapterRepository().save(chapter);
            return chapter;
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody Map<String, Integer> data) {
        try {
            var chapter = repo.getChapterRepository().findById(data.get("chapterID")).get();
            repo.getChapterRepository().deleteById(chapter.getID());
            chapterService.reIndexAllChapterByCourseID(chapter.getCourseID());
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    @PostMapping("/update")
    public Chapter update(@RequestBody Chapter chapter1) {
        try {
            Chapter chapter = repo.getChapterRepository().findById(chapter1.getID()).get();
            chapter.setName(chapter1.getName());
            chapter.setDescription(chapter1.getDescription());
            chapter.setIndex(chapter1.getIndex());
            chapter = repo.getChapterRepository().save(chapter);
            return chapter;
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/reIndexs")
    public boolean update(@RequestBody Map<String, Integer> data) {
        try {
            int size = data.get("size");
            int courseID = data.get("courseID");
            for (int i = 0; i < size; i++) {
                int id = data.get("id_" + i);
                int index = data.get("index_" + i);
                var chapter = repo.getChapterRepository().findById(id).get();
                chapter.setIndex(index);
                repo.getChapterRepository().save(chapter);
            }
            return true;
        } catch (Exception e) {

        }
        return false;
    }
}

package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.Chapter;
import com.swp_project_g4.Model.Course;
import com.swp_project_g4.Model.Instructor;
import com.swp_project_g4.Repository.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ChapterService {
    @Autowired
    private Repo repo;

    public boolean reIndexAllChapterByCourseID(int courseID) {
        try {
            var chapters = repo.getCourseRepository().findById(courseID).get().getChapters();
            chapters.sort(Comparator.comparingInt(Chapter::getIndex));
            int tmp = 0;
            for (var chapter : chapters) {
                chapter.setIndex(++tmp);
                repo.getChapterRepository().save(chapter);
            }
            return true;
        } catch (Exception e) {

        }
        return false;
    }

}

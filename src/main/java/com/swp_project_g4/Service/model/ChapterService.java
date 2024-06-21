package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.Chapter;
import com.swp_project_g4.Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class ChapterService {
    @Autowired
    private Repository repository;

    public boolean reIndexAllChapterByCourseID(int courseID) {
        try {
            var chapters = repository.getCourseRepository().findById(courseID).get().getChapters();
            chapters.sort(Comparator.comparingInt(Chapter::getIndex));
            int tmp = 0;
            for (var chapter : chapters) {
                chapter.setIndex(++tmp);
                repository.getChapterRepository().save(chapter);
            }
            return true;
        } catch (Exception e) {

        }
        return false;
    }

}

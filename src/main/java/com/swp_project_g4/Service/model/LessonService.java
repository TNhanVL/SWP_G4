package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.Lesson;
import com.swp_project_g4.Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class LessonService {
    @Autowired
    private Repository repository;

    public boolean reIndexAllLessonByChapterID(int chapterID) {
        try {
            var lessons = repository.getChapterRepository().findById(chapterID).get().getLessons();
            lessons.sort(Comparator.comparingInt(Lesson::getIndex));
            int tmp = 0;
            for (var lesson : lessons) {
                lesson.setIndex(++tmp);
                repository.getLessonRepository().save(lesson);
            }
            return true;
        } catch (Exception e) {

        }
        return false;
    }

}

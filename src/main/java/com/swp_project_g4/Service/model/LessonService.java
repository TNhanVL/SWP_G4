package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.Lesson;
import com.swp_project_g4.Model.Chapter;
import com.swp_project_g4.Model.Instructor;
import com.swp_project_g4.Repository.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class LessonService {
    @Autowired
    private Repo repo;

    public boolean reIndexAllLessonByChapterID(int chapterID) {
        try {
            var lessons = repo.getChapterRepository().findById(chapterID).get().getLessons();
            lessons.sort(Comparator.comparingInt(Lesson::getIndex));
            int tmp = 0;
            for (var lesson : lessons) {
                lesson.setIndex(++tmp);
                repo.getLessonRepository().save(lesson);
            }
            return true;
        } catch (Exception e) {

        }
        return false;
    }

}

package com.swp_project_g4.Service;

import com.swp_project_g4.Model.CourseProgress;
import com.swp_project_g4.Repository.CourseProgressRepository;
import com.swp_project_g4.Repository.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LessonProgressService {
    @Autowired
    private Repo repo;

    public boolean markLessonCompleted(int learnerID, int lessonID) {
        try {
            var learner = repo.getLearnerRepository().findById(learnerID).orElseThrow();
            var lesson = repo.getLessonRepository().findById(lessonID).orElseThrow();
            var chapter = lesson.getChapter();
            var courseProgress = repo.getCourseProgressRepository().findByCourseIDAndLearnerID(chapter.getCourseID(), learnerID).orElseThrow();
            var chapterProgress = repo.getChapterProgressRepository().findByChapterIDAndCourseProgressID(chapter.getID(), courseProgress.getID()).orElseThrow();
            var lessonProgress = repo.getLessonProgressRepository().findByLessonIDAndChapterProgressID(lessonID, chapterProgress.getID()).orElseThrow();
            if (!lessonProgress.isCompleted()) {
                lessonProgress.setCompleted(true);
                repo.getLessonProgressRepository().save(lessonProgress);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

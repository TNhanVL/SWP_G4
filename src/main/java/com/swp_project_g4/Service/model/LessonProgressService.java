package com.swp_project_g4.Service.model;

import com.swp_project_g4.Repository.Repo;
import com.swp_project_g4.Service.model.CourseProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonProgressService {
    @Autowired
    private Repo repo;
    @Autowired
    private CourseProgressService courseProgressService;

    public boolean markLessonCompleted(int learnerID, int lessonID) {
        try {
            //check learner exist
            var learner = repo.getLearnerRepository().findById(learnerID).orElseThrow();
            var lesson = repo.getLessonRepository().findById(lessonID).orElseThrow();
            var chapter = lesson.getChapter();
            var courseProgress = repo.getCourseProgressRepository().findByCourseIDAndLearnerID(chapter.getCourseID(), learnerID).orElseThrow();
            var chapterProgress = repo.getChapterProgressRepository().findByChapterIDAndCourseProgressID(chapter.getID(), courseProgress.getID()).orElseThrow();
            var lessonProgress = repo.getLessonProgressRepository().findByLessonIDAndChapterProgressID(lessonID, chapterProgress.getID()).orElseThrow();
            //set completed
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

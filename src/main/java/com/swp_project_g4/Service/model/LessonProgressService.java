package com.swp_project_g4.Service.model;

import com.swp_project_g4.Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonProgressService {
    @Autowired
    private Repository repository;
    @Autowired
    private CourseProgressService courseProgressService;

    public boolean markLessonCompleted(int learnerId, int lessonId) {
        try {
            //check learner exist
            var learner = repository.getLearnerRepository().findById(learnerId).orElseThrow();
            var lesson = repository.getLessonRepository().findById(lessonId).orElseThrow();
            var chapter = lesson.getChapter();
            var courseProgress = repository.getCourseProgressRepository().findByCourseIdAndLearnerId(chapter.getCourseId(), learnerId).orElseThrow();
            var chapterProgress = repository.getChapterProgressRepository().findByChapterIdAndCourseProgressID(chapter.getID(), courseProgress.getID()).orElseThrow();
            var lessonProgress = repository.getLessonProgressRepository().findByLessonIdAndChapterProgressID(lessonId, chapterProgress.getID()).orElseThrow();
            //set completed
            if (!lessonProgress.isCompleted()) {
                lessonProgress.setCompleted(true);
                repository.getLessonProgressRepository().save(lessonProgress);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

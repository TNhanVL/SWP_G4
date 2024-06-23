package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.Lesson;
import com.swp_project_g4.Model.LessonProgress;
import com.swp_project_g4.Repository.LessonProgressRepository;
import com.swp_project_g4.Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LessonProgressService {
    @Autowired
    private CourseProgressService courseProgressService;
    @Autowired
    private ChapterProgressService chapterProgressService;
    @Autowired
    private LessonProgressRepository lessonProgressRepository;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private LearnerService learnerService;

    public Optional<LessonProgress> findById(int lessonId) {
        return lessonProgressRepository.findById(lessonId);
    }

    public Optional<LessonProgress> findByLessonIdAndChapterProgressId(int lessonId, int chapterProgressId) {
        return lessonProgressRepository.findByLessonIdAndChapterProgressID(lessonId, chapterProgressId);
    }

    public boolean markLessonCompleted(int learnerId, int lessonId) {
        try {
            //check learner exist
            var learner = learnerService.findById(learnerId).orElseThrow();
            var lesson = lessonService.findById(lessonId).orElseThrow();
            var chapter = lesson.getChapter();
            var courseProgress = courseProgressService.findByCourseIdAndLearnerId(chapter.getCourseId(), learnerId).orElseThrow();
            var chapterProgress = chapterProgressService.findByChapterIdAndCourseProgressId(chapter.getID(), courseProgress.getID()).orElseThrow();
            var lessonProgress = lessonProgressRepository.findByLessonIdAndChapterProgressID(lessonId, chapterProgress.getID()).orElseThrow();
            //set completed
            if (!lessonProgress.isCompleted()) {
                lessonProgress.setCompleted(true);
                lessonProgressRepository.save(lessonProgress);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public LessonProgress save(LessonProgress lessonProgress) {
        return lessonProgressRepository.save(lessonProgress);
    }
}

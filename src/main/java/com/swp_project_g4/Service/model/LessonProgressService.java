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

    public Optional<LessonProgress> getById(int lessonId) {
        return lessonProgressRepository.findById(lessonId);
    }

    public Optional<LessonProgress> getByLessonIdAndChapterProgressId(int lessonId, int chapterProgressId) {
        return lessonProgressRepository.findByLessonIdAndChapterProgressID(lessonId, chapterProgressId);
    }

    public boolean markLessonCompleted(int learnerId, int lessonId) {
        try {
            //check learner exist
            var learner = learnerService.getById(learnerId).orElseThrow();
            var lesson = lessonService.getById(lessonId).orElseThrow();
            var chapter = lesson.getChapter();
            var courseProgress = courseProgressService.getByCourseIdAndLearnerId(chapter.getCourseId(), learnerId).orElseThrow();
            var chapterProgress = chapterProgressService.getByChapterIdAndCourseProgressId(chapter.getID(), courseProgress.getID()).orElseThrow();
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

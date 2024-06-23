package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.Chapter;
import com.swp_project_g4.Model.Lesson;
import com.swp_project_g4.Repository.ChapterRepository;
import com.swp_project_g4.Repository.Repository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ChapterService {
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private LessonService lessonService;

    public List<Chapter> findByCourseId(int courseId) {
        return chapterRepository.findAllByCourseId(courseId);
    }

    public Optional<Chapter> findById(int chapterId) {
        return chapterRepository.findById(chapterId);
    }

    public Chapter save(Chapter chapter) {
        return chapterRepository.save(chapter);
    }

    public int getSumTimeOfChapterById(int chapterId) {
        var lessons = lessonService.findAllByChapterId(chapterId);
        int totalTime = 0;
        for (var lesson: lessons) {
            totalTime += lesson.getTime();
        }
        return totalTime;
    }

    public boolean reIndexAllLessonByChapterId(int chapterId) {
        try {
            var lessons = lessonService.findAllByChapterId(chapterId);
            lessons.sort(Comparator.comparingInt(Lesson::getIndex));
            int tmp = 0;
            for (var lesson : lessons) {
                lesson.setIndex(++tmp);
                lessonService.save(lesson);
            }
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    public void deleteById(int chapterId) {
        chapterRepository.deleteById(chapterId);
    }
}

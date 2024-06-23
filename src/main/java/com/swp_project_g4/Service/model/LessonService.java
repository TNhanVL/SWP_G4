package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.Lesson;
import com.swp_project_g4.Repository.LessonRepository;
import com.swp_project_g4.Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class LessonService {
    @Autowired
    private LessonRepository lessonRepository;

    public Optional<Lesson> getById(int lessonId) {
        return lessonRepository.findById(lessonId);
    }

    public Lesson save(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public List<Lesson> getAllByChapterId(int chapterId) {
        return lessonRepository.findAllByChapterId(chapterId);
    }

    public void deleteById(int lessonId) {
        lessonRepository.deleteById(lessonId);
    }
}

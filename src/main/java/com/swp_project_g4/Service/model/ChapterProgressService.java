package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.ChapterProgress;
import com.swp_project_g4.Repository.ChapterProgressRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class ChapterProgressService {
    @Autowired
    private ChapterProgressRepository chapterProgressRepository;

    public Optional<ChapterProgress> findByChapterIdAndCourseProgressId(int chapterId, int courseProgressId) {
        return chapterProgressRepository.findByChapterIdAndCourseProgressID(chapterId, courseProgressId);
    }

    public ChapterProgress save(ChapterProgress chapterProgress) {
        return chapterProgressRepository.save(chapterProgress);
    }
}

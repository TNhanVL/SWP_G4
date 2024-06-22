package com.swp_project_g4.Repository;

import com.swp_project_g4.Model.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonProgressRepository extends JpaRepository<LessonProgress, Integer> {
    Optional<LessonProgress> findByLessonIdAndChapterProgressID(int lessonId, int chapterProgressID);

    void deleteAllByLessonId(int lessonId);
    void deleteAllByChapterProgressID(int chapterProgressID);
}

package com.swp_project_g4.Repository;

import com.swp_project_g4.Model.ChapterProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChapterProgressRepository extends JpaRepository<ChapterProgress, Integer> {
    Optional<ChapterProgress> findByChapterIdAndCourseProgressID(int courseProgressID, int chapterId);
}

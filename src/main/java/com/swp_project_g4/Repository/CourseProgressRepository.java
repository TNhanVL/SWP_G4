package com.swp_project_g4.Repository;

import com.swp_project_g4.Model.CourseProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseProgressRepository extends JpaRepository<CourseProgress, Integer> {
    Optional<CourseProgress> findByCourseIdAndLearnerId(int courseId, int learnerId);

    List<CourseProgress> findAllByLearnerId(int learnerId);

    List<CourseProgress> findAllByCourseId(int courseId);

    List<CourseProgress> findAllByActionAfterCompletedAndCompleted(boolean actionAfterCompleted, boolean completed);

    List<CourseProgress> findAllByLearnerIdAndCompleted(int learnerId, boolean completed);
}

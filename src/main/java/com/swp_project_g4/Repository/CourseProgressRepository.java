package com.swp_project_g4.Repository;

import com.swp_project_g4.Model.CourseProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseProgressRepository extends JpaRepository<CourseProgress, Integer> {
    Optional<CourseProgress> findByCourseIDAndLearnerID(int courseID, int learnerID);

    List<CourseProgress> findByLearnerID(int learnerID);

    List<CourseProgress> findByCourseID(int courseID);

    List<CourseProgress> findByActionAfterCompletedAndCompleted(boolean actionAfterCompleted, boolean completed);

    List<CourseProgress> findByLearnerIDAndCompleted(int learnerID, boolean completed);
}

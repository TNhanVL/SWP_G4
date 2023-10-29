package com.swp_project_g4.Repository;

import com.swp_project_g4.Model.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Integer> {
    List<QuizResult> findByLessonIDAndLessonProgressID(int lessonID, int lessonProgressID);

    List<QuizResult> findByFinished(boolean finished);
}

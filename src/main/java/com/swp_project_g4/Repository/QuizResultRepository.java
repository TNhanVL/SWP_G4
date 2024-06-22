package com.swp_project_g4.Repository;

import com.swp_project_g4.Model.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Integer> {
    List<QuizResult> findAllByQuizIdAndLessonProgressID(int quizId, int lessonProgressID);

    List<QuizResult> findAllByFinished(boolean finished);
}

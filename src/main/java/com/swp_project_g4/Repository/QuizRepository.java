package com.swp_project_g4.Repository;

import com.swp_project_g4.Model.Quiz;
import com.swp_project_g4.Model.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    Optional<Quiz> findByID(int quizId);
    Optional<Quiz> findByLessonId(int lessonId);
}

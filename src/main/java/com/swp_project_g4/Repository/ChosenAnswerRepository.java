package com.swp_project_g4.Repository;

import com.swp_project_g4.Model.ChosenAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChosenAnswerRepository extends JpaRepository<ChosenAnswer, Integer> {
    Optional<ChosenAnswer> findByQuizResultIDAndAnswerId(int quizResultId, int answerId);
    List<ChosenAnswer> getAllByQuizResultID(int quizResultId);
    void deleteAllByQuizResultIDAndAnswerId(int quizResultId, int answerId);
}

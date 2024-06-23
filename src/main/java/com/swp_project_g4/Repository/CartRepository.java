package com.swp_project_g4.Repository;

import com.swp_project_g4.Model.Cart;
import com.swp_project_g4.Model.Instruct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional findByCourseIdAndLearnerId(int courseId, int learnerId);
    List<Cart> findAllByLearnerId(int learnerId);
    void deleteByCourseIdAndLearnerId(int courseId, int learnerId);
}

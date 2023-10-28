package com.swp_project_g4.Repository;

import com.swp_project_g4.Model.Learner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LearnerRepository extends JpaRepository<Learner, Integer> {
    Optional<Learner> findByUsername(String username);

    Optional<Learner> findByEmail(String email);

    Optional<Learner> findByUsernameAndPassword(String username, String password);
}

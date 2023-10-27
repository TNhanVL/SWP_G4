package com.swp_project_g4.Repository;

import com.swp_project_g4.Model.Learner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearnerRepository extends JpaRepository<Learner, Integer> {
    Learner findUserByUsername(String username);

    Learner findUserByUsernameAndPassword(String username, String password);
}

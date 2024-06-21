package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.Learner;
import com.swp_project_g4.Model.Lesson;
import com.swp_project_g4.Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;

@Service
public class LearnerService {
    @Autowired
    private Repository repository;

    public Optional<Learner> getByUsername(String username) {
        var learner = repository.getLearnerRepository().findByUsername(username);
        return learner;
    }

    public Optional<Learner> getByEmail(String email) {
        var learner = repository.getLearnerRepository().findByEmail(email);
        return learner;
    }
}

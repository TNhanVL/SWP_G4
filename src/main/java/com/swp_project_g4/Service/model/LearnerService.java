package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.Learner;
import com.swp_project_g4.Model.Lesson;
import com.swp_project_g4.Repository.LearnerRepository;
import com.swp_project_g4.Repository.Repository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LearnerService {
    @Autowired
    private LearnerRepository learnerRepository;

    public Optional<Learner> findById(int learnerId) {
        var learner = learnerRepository.findById(learnerId);
        return learner;
    }

    public Optional<Learner> findByUsername(String username) {
        var learner = learnerRepository.findByUsername(username);
        return learner;
    }

    public Optional<Learner> findByEmail(String email) {
        var learner = learnerRepository.findByEmail(email);
        return learner;
    }

    public Optional<Learner> findByUsernameAndPassword(String username, String password) {
        var learner = learnerRepository.findByUsernameAndPassword(username, password);
        return learner;
    }

    public List<Learner> findAll() {
        return learnerRepository.findAll();
    }

    public Learner save(Learner learner) {
        return learnerRepository.save(learner);
    }

    public long count() {
        return learnerRepository.count();
    }

    public void deleteById(int learnerId) {
        learnerRepository.deleteById(learnerId);
    }
}

package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.Instructor;
import com.swp_project_g4.Model.Learner;
import com.swp_project_g4.Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InstructorService {
    @Autowired
    private Repository repository;

    public Optional<Instructor> getById(int Id) {
        var instructor = repository.getInstructorRepository().findById(Id);
        return instructor;
    }

    public Optional<Instructor> getByUsername(String username) {
        var instructor = repository.getInstructorRepository().findByUsername(username);
        return instructor;
    }

}

package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.Instructor;
import com.swp_project_g4.Model.Learner;
import com.swp_project_g4.Repository.InstructorRepository;
import com.swp_project_g4.Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {
    @Autowired
    private InstructorRepository instructorRepository;

    public Optional<Instructor> findById(int Id) {
        var instructor = instructorRepository.findById(Id);
        return instructor;
    }

    public Optional<Instructor> findByUsername(String username) {
        var instructor = instructorRepository.findByUsername(username);
        return instructor;
    }

    public List<Instructor> getAllByOrganizationId(int organizationId) {
        return instructorRepository.findAllByOrganizationId(organizationId);
    }

    public List<Instructor> getAll() {
        return instructorRepository.findAll();
    }

    public Instructor save(Instructor instructor) {
        return instructorRepository.save(instructor);
    }
}

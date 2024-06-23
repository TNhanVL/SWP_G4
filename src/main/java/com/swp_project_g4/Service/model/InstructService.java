package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.Instruct;
import com.swp_project_g4.Model.Instructor;
import com.swp_project_g4.Repository.InstructRepository;
import com.swp_project_g4.Repository.InstructorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InstructService {
    @Autowired
    private InstructRepository instructRepository;

    public List<Instruct> findAllByInstructorId(int instructorId) {
        return instructRepository.findAllByInstructorId(instructorId);
    }

    public Instruct save(Instruct instruct) {
        return instructRepository.save(instruct);
    }
}

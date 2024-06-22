package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.Admin;
import com.swp_project_g4.Model.Learner;
import com.swp_project_g4.Repository.AdminRepository;
import com.swp_project_g4.Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public Optional<Admin> getByUsername(String username) {
        var admin = adminRepository.findByUsername(username);
        return admin;
    }

}

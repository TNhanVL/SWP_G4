package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.Instructor;
import com.swp_project_g4.Model.Organization;
import com.swp_project_g4.Repository.OrganizationRepository;
import com.swp_project_g4.Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrganizationService {
    @Autowired
    private OrganizationRepository organizationRepository;

    public Optional<Organization> getByUsername(String username) {
        var organization = organizationRepository.findByUsername(username);
        return organization;
    }

}
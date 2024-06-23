package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.Instructor;
import com.swp_project_g4.Model.Organization;
import com.swp_project_g4.Repository.OrganizationRepository;
import com.swp_project_g4.Repository.Repository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrganizationService {
    @Autowired
    private OrganizationRepository organizationRepository;

    public Optional<Organization> findById(int organizationId) {
        var organization = organizationRepository.findById(organizationId);
        return organization;
    }

    public Optional<Organization> findByUsername(String username) {
        var organization = organizationRepository.findByUsername(username);
        return organization;
    }

    public List<Organization> findAll() {
        return organizationRepository.findAll();
    }

    public Organization save(Organization organization) {
        return organizationRepository.save(organization);
    }
}

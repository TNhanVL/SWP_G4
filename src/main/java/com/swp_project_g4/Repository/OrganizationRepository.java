package com.swp_project_g4.Repository;

import com.swp_project_g4.Model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
    Optional<Organization> findByUsername(String username);

    Optional<Organization> findByUsernameAndPassword(String username, String password);
}

package com.swp_project_g4.Repository;

import com.swp_project_g4.Model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
    Organization findUserByUsername(String username);

    Organization findUserByUsernameAndPassword(String username, String password);
}

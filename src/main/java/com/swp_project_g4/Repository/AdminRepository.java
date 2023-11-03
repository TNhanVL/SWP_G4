package com.swp_project_g4.Repository;

import com.swp_project_g4.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Optional<Admin> findByUsername(String username);

    Optional<Admin> findByUsernameAndPassword(String username, String password);
}

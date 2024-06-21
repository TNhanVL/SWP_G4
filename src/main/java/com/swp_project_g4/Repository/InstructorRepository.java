package com.swp_project_g4.Repository;

import com.swp_project_g4.Model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {

    Optional<Instructor> findByEmail(String email);

    Optional<Instructor> findByUsername(String username);

    List<Instructor> findAllByOrganizationID(int organizationID);


    Optional<Instructor> findByUsernameAndPassword(String username, String password);

}

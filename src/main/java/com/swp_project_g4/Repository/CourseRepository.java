package com.swp_project_g4.Repository;

import com.swp_project_g4.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findAllByOrganizationID(int organizationID);
}

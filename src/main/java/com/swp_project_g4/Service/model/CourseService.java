package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.Course;
import com.swp_project_g4.Model.Instructor;
import com.swp_project_g4.Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    @Autowired
    private Repository repository;

    public List<Course> getAllCreatedCourses(int instructorID) {
        var instructor = repository.getInstructorRepository().findById(instructorID).get();
        var instructs = instructor.getInstructs();
        var courses = new ArrayList<Course>();
        for (var instruct : instructs) {
            courses.add(instruct.getCourse());
        }
        return courses;
    }

    public List<Instructor> getAllInstructors(int courseID) {
        var instructs = repository.getCourseRepository().findById(courseID).get().getInstructs();
        var instructors = new ArrayList<Instructor>();
        for (var instruct : instructs) {
            instructors.add(instruct.getInstructor());
        }
        return instructors;
    }

}

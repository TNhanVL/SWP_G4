package com.swp_project_g4.RestController;

import com.swp_project_g4.Model.Course;
import com.swp_project_g4.Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:8080/", "http://localhost:3000/"}, allowCredentials = "true")
@RestController
@RequestMapping("api/course")
public class CourseRestController {
    @Autowired
    private Repository repository;

    @PostMapping("/getByCourseId")
    public Course getByCourseId(@RequestBody Map<String, Integer> data) {
        return repository.getCourseRepository().findById(data.get("courseId")).orElse(null);
    }

    @GetMapping("/getAll")
    public List<Course> getAll() {
        return repository.getCourseRepository().findAll();
    }

    @PostMapping("/update")
    public Course update(@RequestBody Course course1) {
        try {
            Course course = repository.getCourseRepository().findById(course1.getID()).get();
            course.setName(course1.getName());
            course.setDescription(course1.getDescription());
            course.setPrice(course1.getPrice());
            course = repository.getCourseRepository().save(course);
            return course;
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("searchCourse")
    public ArrayList<Course> searchCourse(@RequestParam String searchString) {
        return null;
    }

    @GetMapping("fetchCourse")
    public List<Course> fetchCourse() {
        return repository.getCourseRepository().findAll();
    }
}

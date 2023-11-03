package com.swp_project_g4.RestController;

import com.swp_project_g4.Model.Course;
import com.swp_project_g4.Repository.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/course")
public class CourseRestController {
    @Autowired
    private Repo repo;

    @GetMapping("/getAll")
    public List<Course> getAll(){
        return repo.getCourseRepository().findAll();
    }
}

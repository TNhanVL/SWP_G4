package com.swp_project_g4.Service;

import com.swp_project_g4.Model.Course;
import com.swp_project_g4.Repository.Repository;
import com.swp_project_g4.Service.model.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class CourseServiceTest {
    @Autowired
    private Repository repository;
    @Autowired
    private CourseService courseService;

    @Test
    void getAllCourse() {
        var b = repository.getCourseRepository().findAll();
        System.out.println(b);
    }

    @Test
    void getSumTimeOfCourse() {
        var b = courseService.getSumTimeOfCourseById(1);
        System.out.println(b);
    }
}
package com.swp_project_g4.Service;

import com.swp_project_g4.Model.CourseProgress;
import com.swp_project_g4.Repository.CourseProgressRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class CourseProgressServiceTest {
    @Autowired
    private CourseProgressService courseProgressService;
    @Autowired
    private CourseProgressRepository courseProgressRepository;

    @Test
    void getAllCourseProgresses() {
//        var a = new CourseProgress(0, 1, 1, false, 0, false, new Date(), false, 0);
//        courseProgressService.addCourseProgress(a);
        var b = courseProgressRepository.findById(2);
        System.out.println(b.isPresent());
    }
}
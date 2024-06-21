package com.swp_project_g4.Service;

import com.swp_project_g4.Model.Course;
import com.swp_project_g4.Repository.Repository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class QuizServiceTest {
    @Autowired
    private Repository repository;

    @Test
    void getAllCourseProgresses() {
        var b = repository.getLearnerRepository().findByUsernameAndPassword("ttnhan", "0cc175b9c0f1b6a831c399e269772661");
        System.out.println(b);
    }

    @Test
    void getInstructorByID() {
        Course course = repository.getCourseRepository().findById(1).get();
        course = repository.getCourseRepository().save(course);
        System.out.println(course);
    }
}
package com.swp_project_g4.Service;

import com.swp_project_g4.Model.CourseProgress;
import com.swp_project_g4.Repository.CourseProgressRepository;
import com.swp_project_g4.Repository.Repo;
import com.swp_project_g4.Repository.UserRepository;
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
    private Repo repo;

    @Test
    void getAllCourseProgresses() {
        var b = repo.getUserRepository().findUserByUsernameAndPassword("ttnhan", "0cc175b9c0f1b6a831c399e269772661");
        System.out.println(b);
    }
}
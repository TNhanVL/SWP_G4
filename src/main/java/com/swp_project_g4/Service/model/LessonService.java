package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.Course;
import com.swp_project_g4.Model.Instructor;
import com.swp_project_g4.Repository.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LessonService {
    @Autowired
    private Repo repo;

    boolean deleteLesson(int lessonID) {
        try {

            return true;
        } catch (Exception e) {

        }
        return false;
    }

}

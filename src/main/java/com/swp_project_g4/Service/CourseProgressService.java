//package com.swp_project_g4.Service;
//
//import com.swp_project_g4.Model.CourseProgress;
//import com.swp_project_g4.Repository.CourseProgressRepository;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Service;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Service
//public class CourseProgressService {
//    @Autowired
//    private CourseProgressRepository courseProgressRepository;
//
//
//    public List<CourseProgress> getAllCourseProgresses() {
//        var res = new ArrayList<CourseProgress>();
//        courseProgressRepository.findAll().forEach(res::add);
//        return res;
//    }
//
//    public void addCourseProgress(CourseProgress courseProgress) {
//        courseProgressRepository.save(courseProgress);
//    }
//
//}

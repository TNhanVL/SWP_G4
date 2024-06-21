package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.CourseProgress;
import com.swp_project_g4.Repository.Repository;
import com.swp_project_g4.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseProgressService {
    @Autowired
    private Repository repository;
    @Autowired
    private EmailService emailService;

    public void afterCompleted(CourseProgress courseProgress) {
        if (!courseProgress.isCompleted()) return;
        courseProgress.setActionAfterCompleted(true);
        repository.getCourseProgressRepository().save(courseProgress);

        emailService.sendCompleteCourseEmail(courseProgress.getLearner(), courseProgress.getCourse());
    }

    public void enroll(CourseProgress courseProgress) {
        if (courseProgress.isEnrolled()) return;
        courseProgress.setEnrolled(true);
        repository.getCourseProgressRepository().save(courseProgress);

        emailService.sendEnrollEmail(courseProgress.getLearner(), courseProgress.getCourse());
    }
}

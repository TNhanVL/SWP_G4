package com.swp_project_g4.Service;

import com.swp_project_g4.Model.CourseProgress;
import com.swp_project_g4.Repository.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseProgressService {
    @Autowired
    private Repo repo;

    public void afterCompleted(CourseProgress courseProgress) {
        if (!courseProgress.isCompleted()) return;
        courseProgress.setActionAfterCompleted(true);
        repo.getCourseProgressRepository().save(courseProgress);

        EmailService.sendCompleteCourseEmail(courseProgress.getLearner(), courseProgress.getCourse());
    }

}

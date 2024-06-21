package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.CourseProgress;
import com.swp_project_g4.Repository.CourseProgressRepository;
import com.swp_project_g4.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseProgressService {
    @Autowired
    private CourseProgressRepository courseProgressRepository;
    @Autowired
    private EmailService emailService;

    public List<CourseProgress> getByLearnerId(int learnerId) {
        var courseProgress = courseProgressRepository.findAllByLearnerId(learnerId);
        return courseProgress;
    }

    public List<CourseProgress> getByLearnerIdAndCompleted(int learnerId, boolean completed) {
        var courseProgress = courseProgressRepository.findAllByLearnerIdAndCompleted(learnerId, completed);
        return courseProgress;
    }

    public void doActionAfterCompleted(CourseProgress courseProgress) {
        if (!courseProgress.isCompleted()) return;
        courseProgress.setActionAfterCompleted(true);
        courseProgressRepository.save(courseProgress);

        emailService.sendCompleteCourseEmail(courseProgress.getLearner(), courseProgress.getCourse());
    }

    public void enroll(CourseProgress courseProgress) {
        if (courseProgress.isEnrolled()) return;
        courseProgress.setEnrolled(true);
        courseProgressRepository.save(courseProgress);

        emailService.sendEnrollEmail(courseProgress.getLearner(), courseProgress.getCourse());
    }
}

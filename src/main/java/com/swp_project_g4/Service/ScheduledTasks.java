package com.swp_project_g4.Service;

import com.swp_project_g4.Repository.Repository;
import com.swp_project_g4.Service.model.CourseProgressService;
import com.swp_project_g4.Service.model.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTasks {
    @Autowired
    private Repository repository;
    @Autowired
    private QuizService quizService;
    @Autowired
    private CourseProgressService courseProgressService;

    @Scheduled(fixedRate = 5000)
    public void endQuizSchedule() {
        var notEndQuizs = repository.getQuizResultRepository().findByFinished(false);
        for (var quizResult : notEndQuizs) {
            if (quizResult.getEndAt().before(new Date())) {
                quizService.endAQuiz(quizResult);
            }
        }
    }

    @Scheduled(fixedRate = 5000)
    public void actionAfterCompletedCourseSchedule() {
        var notActionCourseProgresses = repository.getCourseProgressRepository().findByActionAfterCompletedAndCompleted(false, true);
        for (var courseProgress : notActionCourseProgresses) {
            courseProgressService.doActionAfterCompleted(courseProgress);
        }
    }
}
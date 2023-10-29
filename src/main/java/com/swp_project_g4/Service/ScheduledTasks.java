package com.swp_project_g4.Service;

import com.swp_project_g4.Repository.Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasks {
    @Autowired
    private Repo repo;
    @Autowired
    private QuizService quizService;
    @Autowired
    private CourseProgressService courseProgressService;

    @Scheduled(fixedRate = 5000)
    public void endQuizSchedule() {
        var notEndQuizs = repo.getQuizResultRepository().findByFinished(false);
        for (var quizResult : notEndQuizs) {
            if (quizResult.getEndAt().before(new Date())) {
                quizService.endAQuiz(quizResult);
            }
        }
    }

    @Scheduled(fixedRate = 5000)
    public void actionAfterCompletedCourseSchedule() {
        var notActionCourseProgresses = repo.getCourseProgressRepository().findByActionAfterCompletedAndCompleted(false, true);
        for (var courseProgress : notActionCourseProgresses) {
            courseProgressService.afterCompleted(courseProgress);
        }
    }
}
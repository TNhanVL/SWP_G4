package com.swp_project_g4.Repository;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
public class Repository {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private ChapterProgressRepository chapterProgressRepository;
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private ChosenAnswerRepository chosenAnswerRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CourseProgressRepository courseProgressRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private InstructRepository instructRepository;
    @Autowired
    private LearnerRepository learnerRepository;
    @Autowired
    private LessonProgressRepository lessonProgressRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuizResultRepository quizResultRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private TransactionRepository transactionRepository;

}

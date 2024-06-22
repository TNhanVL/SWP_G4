package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.Lesson;
import com.swp_project_g4.Model.Question;
import com.swp_project_g4.Repository.LessonRepository;
import com.swp_project_g4.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> getAllByLessonId(int lessonId) {
        return questionRepository.findAllByLessonId(lessonId);
    }
}

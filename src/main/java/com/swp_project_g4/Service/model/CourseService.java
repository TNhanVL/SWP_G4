package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.Chapter;
import com.swp_project_g4.Model.Course;
import com.swp_project_g4.Model.CourseProgress;
import com.swp_project_g4.Model.Instructor;
import com.swp_project_g4.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private InstructorService instructorService;

    public Optional<Course> getById(int courseId) {
        return courseRepository.findById(courseId);
    }

    public List<Course> getAll() {
        var courses = courseRepository.findAll();
        return courses;
    }

    public List<Course> getAllCreatedCourses(int instructorID) {
        var instructor = instructorService.getById(instructorID).get();
        var instructs = instructor.getInstructs();
        var courses = new ArrayList<Course>();
        for (var instruct : instructs) {
            courses.add(instruct.getCourse());
        }
        return courses;
    }

    public List<Instructor> getAllInstructors(int courseID) {
        var instructs = courseRepository.findById(courseID).get().getInstructs();
        var instructors = new ArrayList<Instructor>();
        for (var instruct : instructs) {
            instructors.add(instruct.getInstructor());
        }
        return instructors;
    }
    
    public Integer getSumTimeOfCourseById(int courseId) {
        var courseOptional = courseRepository.findById(courseId);
        if(courseOptional.isEmpty()) return 0;
        var course = courseOptional.get();

        int totalTime = 0;

        for (var chapter: chapterService.getByCourseId(courseId) ) {
            totalTime += chapterService.getSumTimeOfChapterById(chapter.getID());
        }
        return totalTime;
    }

    public void save(Course course) {
        courseRepository.save(course);
    }

    public boolean reIndexAllChapterByCourseID(int courseID) {
        try {
            var chapters = chapterService.getByCourseId(courseID);
            chapters.sort(Comparator.comparingInt(Chapter::getIndex));
            int tmp = 0;
            for (var chapter : chapters) {
                chapter.setIndex(++tmp);
                chapterService.save(chapter);
            }
            return true;
        } catch (Exception e) {

        }
        return false;
    }
}

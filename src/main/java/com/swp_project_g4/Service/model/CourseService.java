package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.Chapter;
import com.swp_project_g4.Model.Course;
import com.swp_project_g4.Model.CourseProgress;
import com.swp_project_g4.Model.Instructor;
import com.swp_project_g4.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private InstructorService instructorService;

    public Optional<Course> findById(int courseId) {
        return courseRepository.findById(courseId);
    }

    public List<Course> findAll() {
        var courses = courseRepository.findAll();
        return courses;
    }

    public List<Course> findAllCreatedCourses(int instructorId) {
        var instructor = instructorService.findById(instructorId).get();
        var instructs = instructor.getInstructs();
        var courses = new ArrayList<Course>();
        for (var instruct : instructs) {
            courses.add(instruct.getCourse());
        }
        return courses;
    }

    public List<Course> findAllByOrganizationId(int organizationId) {
        return courseRepository.findAllByOrganizationId(organizationId);
    }

    public List<Instructor> findAllInstructors(int courseId) {
        var instructs = courseRepository.findById(courseId).get().getInstructs();
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

        for (var chapter: chapterService.findByCourseId(courseId) ) {
            totalTime += chapterService.getSumTimeOfChapterById(chapter.getID());
        }
        return totalTime;
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public boolean reIndexAllChapterByCourseId(int courseId) {
        try {
            var chapters = chapterService.findByCourseId(courseId);
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

package com.swp_project_g4.Database;

import com.swp_project_g4.Model.Course;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseDAOTest {

    @Test
    void searchCoursesWithEmptyName() {
        CourseDAO courseDAO = new CourseDAO();

        ArrayList<Course> courses = courseDAO.searchCourses("");

        assertEquals(12, courses.size());
    }

    @Test
    void searchCoursesWithCorrectName() {
        CourseDAO courseDAO = new CourseDAO();

        String correctName = "Dekiru Nihongo";
        ArrayList<Course> courses = courseDAO.searchCourses(correctName);

        assertEquals(1, courses.size()); // có nhiều khóa học bị trùng thì lỗi!!!
        assertEquals(correctName, courses.get(0).getName());
    }

    @Test
    void searchCoursesWithIncorrectName() {
        CourseDAO courseDAO = new CourseDAO();

        String incorrectName = "Basic Java";
        ArrayList<Course> courses = courseDAO.searchCourses(incorrectName);

        assertEquals(0, courses.size());
    }
}

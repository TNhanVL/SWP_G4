package com.swp_project_g4.Database;

import com.swp_project_g4.Model.Course;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class CourseDAOTest {

    @Test
    void searchCoursesWithEmptyName() throws SQLException, ClassNotFoundException {
        CourseDAO courseDAO = new CourseDAO();

        ArrayList<Course> courses = courseDAO.searchCourses("");

        assertEquals(12, courses.size());
    }

    @Test
    void searchCoursesWithCorrectName() throws SQLException, ClassNotFoundException {
        CourseDAO courseDAO = new CourseDAO();

        String correctName = "PYTHON FOR BEGINNER";
        ArrayList<Course> courses = courseDAO.searchCourses(correctName);

        assertEquals(1, courses.size());
        assertEquals(correctName, courses.get(0).getName());
    }

    @Test
    void searchCoursesWithIncorrectName() throws SQLException, ClassNotFoundException {
        CourseDAO courseDAO = new CourseDAO();

        String incorrectName = "Basic Java";
        ArrayList<Course> courses = courseDAO.searchCourses(incorrectName);

        assertEquals(0, courses.size());
    }
}

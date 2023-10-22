package com.swp_project_g4.Database;

import com.swp_project_g4.Model.Course;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void searchCoursesWithPartialName() throws SQLException, ClassNotFoundException {
        CourseDAO courseDAO = new CourseDAO();

        String name = "Java";
        ArrayList<Course> courses = courseDAO.searchCourses(name);

        // Kiểm tra xem danh sách khóa học trả về có ít nhất 6 khóa học hay không
//        assertThat(courses.size()).isGreaterThanOrEqualTo(6);
        assertEquals(true, courses.size() >= 6);


        // Kiểm tra xem tên của tất cả các khóa học trong danh sách có chứa tên "Java" hay không
        for (Course course : courses) {
            assertEquals(true, course.getName().contains(name));
//            assertThat(course.getName().contains(name)).isTrue();
        }
    }
}

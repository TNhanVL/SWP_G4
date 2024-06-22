package com.swp_project_g4.Database;

import com.swp_project_g4.Model.Course;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CourseDAOTest {

    // Test the search course function
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
        ArrayList<Course> courses = CourseDAO.searchCourses(correctName);
        assertEquals(1, courses.size());
        assertEquals(correctName, courses.get(0).getName());
    }

    @Test
    void searchCoursesWithIncorrectName() {
        CourseDAO courseDAO = new CourseDAO();

        String incorrectName = "Basic Java";
        ArrayList<Course> courses = CourseDAO.searchCourses(incorrectName);

        assertEquals(0, courses.size());
    }

    @Test
    void searchCoursesWithPartialName() {
        CourseDAO courseDAO = new CourseDAO();
        String name = "Java";
        ArrayList<Course> courses = CourseDAO.searchCourses(name);

//        assertThat(courses.size()).isGreaterThanOrEqualTo(6);
        assertTrue(courses.size() >= 6);

        for (Course course : courses) {
            assertTrue(course.getName().contains(name));
//            assertThat(course.getName().contains(name)).isTrue();
        }
    }

    @Test
    void addCourse_test_1() {
        int organizationId = 1;
        String name = "";
        String picture = "";
        double price = 0;
        boolean res = CourseDAO.addCourse(organizationId, name, picture, price);
        assertFalse(res);
    }

    @Test
    void addCourse_test_2() {
        int organizationId = 1;
        String name = "Nihongo";
        String picture = "";
        double price = 0;
        boolean res = CourseDAO.addCourse(organizationId, name, picture, price);
        assertTrue(res);
    }

    @Test
    void addCourse_test_3() {
        int organizationId = 1;
        String name = "Nihongo";
        String picture = "a.png";
        double price = 10.234;
        boolean res = CourseDAO.addCourse(organizationId, name, picture, price);
        assertTrue(res);
    }

    @Test
    void addCourse_test_4() {
        int organizationId = 1;
        String name = "Nihongo";
        String picture = "a.png";
        double price = -10.12;
        boolean res = CourseDAO.addCourse(organizationId, name, picture, price);
        assertFalse(res);
    }

    @Test
    void addCourse_test_5() {
        int organizationId = 1;
        String name = "PYTHON FOR BEGINNER";
        String picture = "a.png";
        double price = 4;
        boolean res = CourseDAO.addCourse(organizationId, name, picture, price);
        assertTrue(res);
    }

    @Test
    void addCourse_test_6() {
        int organizationId = 1;
        String name = "PYTHON FOR BEGINNER";
        String picture = "https://www.vitto.vn/sac-hoa-tulip";
        double price = 4;
        boolean res = CourseDAO.addCourse(organizationId, name, picture, price);
        assertTrue(res);
    }

    @Test
    void addCourse_test_7() {
        int organizationId = 1;
        String name = "Nihongo";
        String picture = "a.png";
        double price = 4;
        boolean res = CourseDAO.addCourse(organizationId, name, picture, price);
        assertTrue(res);
    }

    @Test
    void addCourse_test_8() {
        int organizationId = 1;
        String name = "Nihongo";
        String picture = "https://www.vitto.vn/sac-hoa-tulip";
        double price = 4;
        boolean res = CourseDAO.addCourse(organizationId, name, picture, price);
        assertTrue(res);
    }

    @Test
    void addCourse_test_9() {
        int organizationId = 1;
        String name = "PYTHON FOR BEGINNER";
        String picture = "https://www.vitto.vn/sac-hoa-tulip";
        double price = 10.234;
        boolean res = CourseDAO.addCourse(organizationId, name, picture, price);
        assertTrue(res);
    }

    @Test
    void addCourse_test_10() {
        int organizationId = -12;
        String name = "Nihongo";
        String picture = "a.png";
        double price = 4;
        boolean res = CourseDAO.addCourse(organizationId, name, picture, price);
        assertFalse(res);
    }

    @Test
    void addCourse_test_11() {
        int organizationId = -12;
        String name = "";
        String picture = "";
        double price = 0;
        boolean res = CourseDAO.addCourse(organizationId, name, picture, price);
        assertFalse(res);
    }

    @Test
    void addCourse_test_12() {
        int organizationId = -12;
        String name = "PYTHON FOR BEGINNER";
        String picture = "https://www.vitto.vn/sac-hoa-tulip";
        double price = 10.234;
        boolean res = CourseDAO.addCourse(organizationId, name, picture, price);
        assertFalse(res);
    }

    @Test
    void addCourse_test_13() {
        int organizationId = 1607;
        String name = "";
        String picture = "";
        double price = -10.12;
        boolean res = CourseDAO.addCourse(organizationId, name, picture, price);
        assertFalse(res);
    }

    @Test
    void addCourse_test_14() {
        int organizationId = 1607;
        String name = "Nihongo";
        String picture = "a.png";
        double price = 10.234;
        boolean res = CourseDAO.addCourse(organizationId, name, picture, price);
        assertFalse(res);
    }

    @Test
    void addCourse_test_15() {
        int organizationId = 1607;
        String name = "PYTHON FOR BEGINNER";
        String picture = "https://www.vitto.vn/sac-hoa-tulip";
        double price = 4;
        boolean res = CourseDAO.addCourse(organizationId, name, picture, price);
        assertFalse(res);
    }
}


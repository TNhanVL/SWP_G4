package com.swp_project_g4.Database;

import com.swp_project_g4.Model.Learner;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class LearnerDAOTest {
    @Test
    void checkUserPassword_test_1() {
        boolean res = LearnerDAO.checkUserPassword("", "", false);
        assertFalse(res);
    }

    @Test
    void checkUserPassword_test_2() {
        boolean res = LearnerDAO.checkUserPassword("", "", true);
        assertFalse(res);
    }

    @Test
    void checkUserPassword_test_3() {
        boolean res = LearnerDAO.checkUserPassword("", "123456", false);
        assertFalse(res);
    }

    @Test
    void checkUserPassword_test_4() {
        boolean res = LearnerDAO.checkUserPassword("", "0cc175b9c0f1b6a831c399e269772661", true);
        assertFalse(res);
    }

    @Test
    void checkUserPassword_test_5() {
        boolean res = LearnerDAO.checkUserPassword("dylan12", "", true);
        assertFalse(res);
    }

    @Test
    void checkUserPassword_test_6() {
        boolean res = LearnerDAO.checkUserPassword("dylan12", "a", false);
        assertFalse(res);
    }

    @Test
    void checkUserPassword_test_7() {
        boolean res = LearnerDAO.checkUserPassword("dylan12", "123456", false);
        assertTrue(res);
    }

    @Test
    void checkUserPassword_test_8() {
        boolean res = LearnerDAO.checkUserPassword("dylan12", "e10adc3949ba59abbe56e057f20f883e", true);
        assertTrue(res);
    }

    @Test
    void checkUserPassword_test_9() {
        boolean res = LearnerDAO.checkUserPassword("ttnhan", "", false);
        assertFalse(res);
    }

    @Test
    void checkUserPassword_test_10() {
        boolean res = LearnerDAO.checkUserPassword("ttnhan", "a", true);
        assertFalse(res);
    }

    @Test
    void checkUserPassword_test_11() {
        boolean res = LearnerDAO.checkUserPassword("ttnhan", "a", false);
        assertTrue(res);
    }

    @Test
    void checkUserPassword_test_12() {
        boolean res = LearnerDAO.checkUserPassword("ttnhan", "123456", true);
        assertFalse(res);
    }

    @Test
    void checkUserPassword_test_13() {
        boolean res = LearnerDAO.checkUserPassword("ttnhan", "0cc175b9c0f1b6a831c399e269772661", true);
        assertTrue(res);
    }

    @Test
    void checkUserPassword_test_14() {
        boolean res = LearnerDAO.checkUserPassword("ttnhan", "0cc175b9c0f1b6a831c399e269772661", false);
        assertFalse(res);
    }

    @Test
    public void testUpdateUser() {
        // Arrange
        Learner learner = new Learner();
        learner.setID(1);
        learner.setPicture("picture.png");
        learner.setUsername("username");
        learner.setPassword("password");
        learner.setEmail("email@example.com");
        learner.setFirstName("First Name");
        learner.setLastName("Last Name");
        learner.setBirthday(new Date());
        learner.setCountryId(1);
        learner.setStatus(1);

        boolean success = LearnerDAO.updateUser(learner);
        assertEquals(true, success);
    }
}
package com.swp_project_g4.Database;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LearnerDAOTest {
    @Test
    void checkUserPassword_test_1() {
        boolean res = LearnerDAO.checkUserPassword("", "", false);
        assertEquals(res, false);
    }

    @Test
    void checkUserPassword_test_2() {
        boolean res = LearnerDAO.checkUserPassword("", "", true);
        assertEquals(res, false);
    }

    @Test
    void checkUserPassword_test_3() {
        boolean res = LearnerDAO.checkUserPassword("", "123456", false);
        assertEquals(res, false);
    }

    @Test
    void checkUserPassword_test_4() {
        boolean res = LearnerDAO.checkUserPassword("", "0cc175b9c0f1b6a831c399e269772661", true);
        assertEquals(res, false);
    }

    @Test
    void checkUserPassword_test_5() {
        boolean res = LearnerDAO.checkUserPassword("dylan12", "", true);
        assertEquals(res, false);
    }

    @Test
    void checkUserPassword_test_6() {
        boolean res = LearnerDAO.checkUserPassword("dylan12", "a", false);
        assertEquals(res, false);
    }

    @Test
    void checkUserPassword_test_7() {
        boolean res = LearnerDAO.checkUserPassword("dylan12", "123456", false);
        assertEquals(res, true);
    }

    @Test
    void checkUserPassword_test_8() {
        boolean res = LearnerDAO.checkUserPassword("dylan12", "e10adc3949ba59abbe56e057f20f883e", true);
        assertEquals(res, true);
    }

    @Test
    void checkUserPassword_test_9() {
        boolean res = LearnerDAO.checkUserPassword("ttnhan", "", false);
        assertEquals(res, false);
    }

    @Test
    void checkUserPassword_test_10() {
        boolean res = LearnerDAO.checkUserPassword("ttnhan", "a", true);
        assertEquals(res, false);
    }

    @Test
    void checkUserPassword_test_11() {
        boolean res = LearnerDAO.checkUserPassword("ttnhan", "a", false);
        assertEquals(res, true);
    }

    @Test
    void checkUserPassword_test_12() {
        boolean res = LearnerDAO.checkUserPassword("ttnhan", "123456", true);
        assertEquals(res, false);
    }

    @Test
    void checkUserPassword_test_13() {
        boolean res = LearnerDAO.checkUserPassword("ttnhan", "0cc175b9c0f1b6a831c399e269772661", true);
        assertEquals(res, true);
    }

    @Test
    void checkUserPassword_test_14() {
        boolean res = LearnerDAO.checkUserPassword("ttnhan", "0cc175b9c0f1b6a831c399e269772661", false);
        assertEquals(res, false);
    }

}
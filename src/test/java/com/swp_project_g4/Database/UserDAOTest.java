package com.swp_project_g4.Database;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    @Test
    void checkUserPassword_test_1() {
        boolean res = UserDAO.checkUserPassword("", "", false);
        assertEquals(res, false);
    }

    @Test
    void checkUserPassword_test_2() {
        boolean res = UserDAO.checkUserPassword("", "", true);
        assertEquals(res, false);
    }

    @Test
    void checkUserPassword_test_3() {
        boolean res = UserDAO.checkUserPassword("", "123456", false);
        assertEquals(res, false);
    }

    @Test
    void checkUserPassword_test_4() {
        boolean res = UserDAO.checkUserPassword("", "0cc175b9c0f1b6a831c399e269772661", true);
        assertEquals(res, false);
    }

    @Test
    void checkUserPassword_test_5() {
        boolean res = UserDAO.checkUserPassword("dylan12", "", true);
        assertEquals(res, false);
    }
}
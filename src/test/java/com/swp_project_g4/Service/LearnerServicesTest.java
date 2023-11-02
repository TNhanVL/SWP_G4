package com.swp_project_g4.Service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class LearnerServicesTest {
    @ParameterizedTest
    @CsvFileSource(resources = "/IsValidInfo_Test_Case.csv", numLinesToSkip = 1)
    void isValidInformation(String name, String phone_number, String email, String birthday, String expected_result) {
        boolean res = UserServices.isValidInformation(name, phone_number, email, birthday);

        boolean expected_output = false;
        if (expected_result.toLowerCase().equals("true")) expected_output = true;
        assertEquals(expected_output, res);
    }

    @Test
    void isValidInformation_test_1() {
        String name = "";
        String phone_number = "";
        String email = "";
        String birthday = "";
        boolean res = UserServices.isValidInformation(name, phone_number, email, birthday);
        assertFalse(res);
    }

    @Test
    void isValidInformation_test_2() {
        String name = "";
        String phone_number = "0908360809";
        String email = "nhan12341184@gmail.com";
        String birthday = "1990-01-01";
        boolean res = UserServices.isValidInformation(name, phone_number, email, birthday);
        assertFalse(res);
    }

    @Test
    void isValidInformation_test_3() {
        String name = "Trần Thanh Nhân";
        String phone_number = "";
        String email = "";
        String birthday = "";
        boolean res = UserServices.isValidInformation(name, phone_number, email, birthday);
        assertFalse(res);
    }

    @Test
    void isValidInformation_test_4() {
        String name = "Trần Thanh Nhân";
        String phone_number = "0908360809";
        String email = "nhan12341184@gmail.com";
        String birthday = "1990-01-01";
        boolean res = UserServices.isValidInformation(name, phone_number, email, birthday);
        assertTrue(res);
    }

    @Test
    void isValidInformation_test_5() {
        String name = "Trần Thanh Nhân";
        String phone_number = "0908360809";
        String email = "nhan12341184@gmail.com";
        String birthday = "210-10-03";
        boolean res = UserServices.isValidInformation(name, phone_number, email, birthday);
        assertFalse(res);
    }

    @Test
    void isValidInformation_test_6() {
        String name = "Trần Thanh Nhân";
        String phone_number = "0908360809";
        String email = "nhan12341184@gmail.com";
        String birthday = "2023-13-32";
        boolean res = UserServices.isValidInformation(name, phone_number, email, birthday);
        assertFalse(res);
    }

    @Test
    void isValidInformation_test_7() {
        String name = "Trần Thanh Nhân";
        String phone_number = "0908360809";
        String email = "nhan12341184@gmail.com";
        String birthday = "11-05-1987";
        boolean res = UserServices.isValidInformation(name, phone_number, email, birthday);
        assertFalse(res);
    }

    @Test
    void isValidInformation_test_8() {
        String name = "Trần Thanh Nhân";
        String phone_number = "0908360809";
        String email = "nhan12341184@gmail.com";
        String birthday = "2015/03/20";
        boolean res = UserServices.isValidInformation(name, phone_number, email, birthday);
        assertFalse(res);
    }

    @Test
    void isValidInformation_test_9() {
        String name = "Trần Thanh Nhân";
        String phone_number = "0908360809";
        String email = "nhan12341184@gmail.com";
        String birthday = "20x3-02-11";
        boolean res = UserServices.isValidInformation(name, phone_number, email, birthday);
        assertFalse(res);
    }

    @Test
    void isValidInformation_test_10() {
        String name = "Trần Thanh Nhân";
        String phone_number = "84939006143";
        String email = "@example.com";
        String birthday = "1990-01-01";
        boolean res = UserServices.isValidInformation(name, phone_number, email, birthday);
        assertFalse(res);
    }

    @Test
    void isValidInformation_test_11() {
        String name = "Dylan Ruan";
        String phone_number = "84939006143";
        String email = "abc123@xyz";
        String birthday = "1990-01-01";
        boolean res = UserServices.isValidInformation(name, phone_number, email, birthday);
        assertFalse(res);
    }

    @Test
    void isValidInformation_test_12() {
        String name = "Dylan Ruan";
        String phone_number = "abc1234xyz";
        String email = "nhan12341184@gmail.com";
        String birthday = "1990-01-01";
        boolean res = UserServices.isValidInformation(name, phone_number, email, birthday);
        assertFalse(res);
    }

    @Test
    void isValidInformation_test_13() {
        String name = "Dylan Ruan";
        String phone_number = "abc1234xyz";
        String email = "abc123@xyz";
        String birthday = "1990-01-01";
        boolean res = UserServices.isValidInformation(name, phone_number, email, birthday);
        assertFalse(res);
    }

    @Test
    void isValidInformation_test_14() {
        String name = "Dylan Ruan";
        String phone_number = "0908360809";
        String email = "nhan12341184@gmail.com";
        String birthday = "1990-01-01";
        boolean res = UserServices.isValidInformation(name, phone_number, email, birthday);
        assertTrue(res);
    }

    @Test
    void isValidInformation_test_15() {
        String name = "Dylan Ruan";
        String phone_number = "84939006143";
        String email = "abc123@xyz";
        String birthday = "2023-13-32";
        boolean res = UserServices.isValidInformation(name, phone_number, email, birthday);
        assertFalse(res);
    }

    @Test
    void isValidInformation_test_16() {
        String name = "Dylan Ruan";
        String phone_number = "abc1234xyz";
        String email = "nhan12341184@gmail.com";
        String birthday = "2023-13-32";
        boolean res = UserServices.isValidInformation(name, phone_number, email, birthday);
        assertFalse(res);
    }

    @Test
    void isValidInformation_test_17() {
        String name = "Dylan Ruan";
        String phone_number = "027035";
        String email = "@example.com";
        String birthday = "1990-01-01";
        boolean res = UserServices.isValidInformation(name, phone_number, email, birthday);
        assertFalse(res);
    }

    @Test
    void isValidInformation_test_18() {
        String name = "Dylan Ruan";
        String phone_number = "093978256385035";
        String email = "abc123@xyz";
        String birthday = "1990-01-01";
        boolean res = UserServices.isValidInformation(name, phone_number, email, birthday);
        assertFalse(res);
    }

}
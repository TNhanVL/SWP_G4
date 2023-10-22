package com.swp_project_g4.Service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServicesTest {

    @Test
    void isValidInformation_test_3() {
        String name = "Trần Thanh Nhân";
        String phone_number = "0908360809";
        String email = "nhan12341184@gmail.com";
        String birthday = "1990-01-01";
        boolean res = UserServices.isValidInformation(name, phone_number, email, birthday);
        assertEquals(true, res);
    }
}
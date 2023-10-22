package com.swp_project_g4.Service;

import com.swp_project_g4.Database.DBConnection;
import com.swp_project_g4.Model.User;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserServices {
    public static boolean isValidInformation(String name, String phone_number, String email, String birthday) {
        try {
            boolean fineName = name.matches("^[\\p{L}\\p{M}]+([\\p{L}\\p{Pd}\\p{Zs}'.]*[\\p{L}\\p{M}])+$|^[\\p{L}\\p{M}]+$");
            if(!fineName){
                return false;
            }
        } catch (Exception e) {

        }
        return true;
    }
}

package com.swp_project_g4.Service;

import com.swp_project_g4.Database.DBConnection;
import com.swp_project_g4.Database.UserDAO;
import com.swp_project_g4.Model.User;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserServices {
    public static boolean isValidInformation(String name, String phone_number, String email, String birthday) {
        try {
            boolean fineName = name.matches("^[\\p{L}\\p{M}]+([\\p{L}\\p{Pd}\\p{Zs}'.]*[\\p{L}\\p{M}])+$|^[\\p{L}\\p{M}]+$");
            if(!fineName){
                Logger.getLogger(UserDAO.class.getName()).log(Level.INFO, "incorrect name");
                return false;
            }

            boolean finePhoneNumber = phone_number.matches("^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$");
            if(!finePhoneNumber){
                Logger.getLogger(UserDAO.class.getName()).log(Level.INFO, "incorrect phone number");
                return false;
            }
        } catch (Exception e) {

        }
        Logger.getLogger(UserDAO.class.getName()).log(Level.INFO, "success");
        return true;
    }
}

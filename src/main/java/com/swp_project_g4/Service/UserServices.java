package com.swp_project_g4.Service;

import com.swp_project_g4.Database.UserDAO;
import com.swp_project_g4.Model.User;
import com.swp_project_g4.Repository.UserRepository;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserServices {

    public static boolean isValidInformation(String name, String phone_number, String email, String birthday) {
        try {
            boolean fineName = name.matches("^[\\p{L}\\p{M}]+([\\p{L}\\p{Pd}\\p{Zs}'.]*[\\p{L}\\p{M}])+$|^[\\p{L}\\p{M}]+$");
            if (!fineName) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.INFO, "invalid name");
                return false;
            }

            boolean finePhoneNumber = phone_number.matches("^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$");
            if (!finePhoneNumber) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.INFO, "invalid phone number");
                return false;
            }

            boolean fineEmail = EmailValidator.getInstance().isValid(email);
            if (!fineEmail) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.INFO, "invalid email");
                return false;
            }

            boolean fineBirthday = GenericValidator.isDate(birthday,"yyyy-MM-dd",true);
            if (!fineBirthday) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.INFO, "invalid birthday");
                return false;
            }


        } catch (Exception e) {

        }
        Logger.getLogger(UserDAO.class.getName()).log(Level.INFO, "valid");
        return true;
    }

}

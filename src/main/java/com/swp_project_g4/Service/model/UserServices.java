package com.swp_project_g4.Service.model;

import com.swp_project_g4.Database.LearnerDAO;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserServices {

    public static boolean isValidInformation(String name, String phone_number, String email, String birthday) {
        boolean valid = true;
        try {
            boolean fineName = name.matches("^[\\p{L}\\p{M}]+([\\p{L}\\p{Pd}\\p{Zs}'.]*[\\p{L}\\p{M}])+$|^[\\p{L}\\p{M}]+$");
            if (!fineName || name == null || name.length() > 60) {
                Logger.getLogger(LearnerDAO.class.getName()).log(Level.INFO, "invalid name");
                valid = false;
            }

            boolean finePhoneNumber = phone_number.matches("^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$");
            if (!finePhoneNumber || phone_number == null) {
                Logger.getLogger(LearnerDAO.class.getName()).log(Level.INFO, "invalid phone number");
                valid = false;
            }

            boolean fineEmail = EmailValidator.getInstance().isValid(email);
            if (!fineEmail || email == null) {
                Logger.getLogger(LearnerDAO.class.getName()).log(Level.INFO, "invalid email");
                valid = false;
            }

            boolean fineBirthday = GenericValidator.isDate(birthday,"yyyy-MM-dd",true)
                    || GenericValidator.isDate(birthday,"yyyy/MM/dd",true);
            if (!fineBirthday || birthday == null) {
                Logger.getLogger(LearnerDAO.class.getName()).log(Level.INFO, "invalid birthday");
                valid = false;
            }


        } catch (Exception e) {

        }
        if(valid) Logger.getLogger(LearnerDAO.class.getName()).log(Level.INFO, "valid");
        return valid;
    }

}

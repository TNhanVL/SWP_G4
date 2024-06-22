/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Database;

import com.swp_project_g4.Model.Instructor;
import com.swp_project_g4.Model.Learner;
import org.junit.platform.commons.function.Try;

import javax.servlet.jsp.tagext.TryCatchFinally;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author TTNhan
 */
public class InstructorDAO extends DBConnection {

    public static Instructor getInstructorByUsername(String username) {
        Instructor instructor = null;
        try {
            //connect to database
            connect();
            statement = conn.prepareStatement("select * from instructor where username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                instructor = new Instructor(
                        resultSet.getInt("instructorId"),
                        resultSet.getInt("organizationId"),
                        resultSet.getString("picture"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getInt("countryId"),
                        resultSet.getInt("status")
                );
            }
            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return instructor;
    }

    public static void main(String[] args) {
        System.out.println(getInstructorByUsername("ttnhan"));
    }
}

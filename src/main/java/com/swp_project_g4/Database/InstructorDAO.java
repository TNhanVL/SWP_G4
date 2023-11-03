/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Database;

import com.swp_project_g4.Model.Instructor;
import com.swp_project_g4.Model.Learner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author TTNhan
 */
public class InstructorDAO extends DBConnection {

    public static Instructor getInstructor(int instructorID) {
        Instructor instructor = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from instructor where instructorID = ?");
            statement.setInt(1, instructorID);
            ResultSet resultSet = statement.executeQuery();



            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return instructor;
    }

    public static void main(String[] args) {
    }
}

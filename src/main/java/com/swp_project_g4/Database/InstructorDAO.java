/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Database;

import com.swp_project_g4.Model.Instructor;
import com.swp_project_g4.Model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TTNhan
 */
public class InstructorDAO extends DBConnection {

    public static Instructor getInstructor(int userID) {
        Instructor instructor = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from instructor where userID = ?");
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = LearnerDAO.getUser(userID);
                instructor = new Instructor(user, resultSet.getInt("organizationID"));
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return instructor;
    }

    public static boolean insertInstructor(Instructor instructor) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("insert into instructor(userID,organizationID) values(?,?)");
            statement.setInt(1, instructor.getID());
            statement.setInt(2, instructor.getOrganizationID());
            statement.execute();
            //Indentify the last ID inserted
            //disconnect to database
            disconnect();

            return true;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean updateInstructor(Instructor instructor) {

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("update instructor set organizationID=? where userID = ?");
            statement.setInt(1, instructor.getOrganizationID());
            statement.setInt(2, instructor.getID());
            statement.executeUpdate();

            //disconnect to database
            disconnect();
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    public static boolean deleteInstructor(int userID) {
        try {
            if (getInstructor(userID) == null) {
                return false;
            }
            connect();
            statement = conn.prepareStatement("delete from instructor where userID=?");
            statement.setInt(1, userID);
            statement.execute();
            disconnect();
            return getInstructor(userID) != null;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void main(String[] args) {
        User user = LearnerDAO.getUser(1);
        Instructor lect = new Instructor(user, 1);
        insertInstructor(lect);
    }
}

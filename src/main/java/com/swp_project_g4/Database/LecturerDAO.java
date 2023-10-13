/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Database;

import com.swp_project_g4.Model.Lecturer;
import com.swp_project_g4.Model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thanh Duong
 */
public class LecturerDAO extends DBConnection {

    public static Lecturer getLecturer(int userID) {
        Lecturer lecturer = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from lecturer where userID = ?");
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = UserDAO.getUser(userID);
                lecturer = new Lecturer(user, resultSet.getInt("organizationID"));
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lecturer;
    }

    public static boolean insertLecturer(Lecturer lecturer) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("insert into lecturer(userID,organizationID) values(?,?)");
            statement.setInt(1, lecturer.getID());
            statement.setInt(2, lecturer.getOrganizationID());
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

    public static boolean updateLecturer(Lecturer lecturer) {

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("update lecturer set organizationID=? where userID = ?");
            statement.setInt(1, lecturer.getOrganizationID());
            statement.setInt(2, lecturer.getID());
            statement.executeUpdate();

            //disconnect to database
            disconnect();
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    public static boolean deleteLecturer(int userID) {
        try {
            if (getLecturer(userID) == null) {
                return false;
            }
            connect();
            statement = conn.prepareStatement("delete from lecturer where userID=?");
            statement.setInt(1, userID);
            statement.execute();
            disconnect();
            return getLecturer(userID) != null;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void main(String[] args) {
        User user = UserDAO.getUser(1);
        Lecturer lect = new Lecturer(user, 1);
        insertLecturer(lect);
    }
}

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

    public static Instructor getInstructor(int instructorID) {
        Instructor instructor = null;
        try {
            //connect to database
            connect();
            statement = conn.prepareStatement("select * from instructor where instructorID = ?");
            statement.setInt(1, instructorID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                instructor  = new Instructor(
                        resultSet.getInt("instructorID"),
                        resultSet.getInt("organizationID"),
                        resultSet.getString("picture"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("countryID"),
                        resultSet.getInt("status")
                );
            }
            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return instructor;
    }
     public static ArrayList<Instructor> getAllInstructor(){
        ArrayList<Instructor> list = new ArrayList<>();
        try {
             statement = conn.prepareStatement("select * from instructor");
             ResultSet resultSet = statement.executeQuery();

             while (resultSet.next()){
                 Instructor instructor = new Instructor(
                         resultSet.getInt("instructorID"),
                         resultSet.getInt("organizationID"),
                         resultSet.getString("picture"),
                         resultSet.getString("username"),
                         resultSet.getString("password"),
                         resultSet.getString("email"),
                         resultSet.getString("first_name"),
                         resultSet.getString("last_name"),
                         resultSet.getInt("countryID"),
                         resultSet.getInt("status")
                 );
             }
        }catch (SQLException ex){
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE,  null,ex);
        }
         return list ;
     }
    public static void main(String[] args) {
    }
}

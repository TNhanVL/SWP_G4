/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Database;

import com.swp_project_g4.Model.Answer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gr4
 */
public class AnswerDAO extends DBConnection {
    
    public static boolean existAnswer(int ID) {
        boolean ok = false;
        try {
            //connect to database
            connect();
            
            statement = conn.prepareStatement("select ID from answer where ID = ?");
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                if (resultSet.getInt("ID") == ID) {
                    ok = true;
                }
            }

            //disconnect to database
            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return result
        return ok;
    }
    
    public static Answer getAnswer(int ID) {
        Answer answer = null;
        
        try {
            //connect to database
            connect();
            
            statement = conn.prepareStatement("select * from answer where ID = ?");
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                answer = new Answer(
                        resultSet.getInt("ID"),
                        resultSet.getString("content"),
                        resultSet.getBoolean("correct"),
                        resultSet.getInt("questionID")
                );
            }
            
            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return answer;
    }
    
    
    
    public static void main(String[] args) {
        System.out.println(getAnswersByQuestionID(1));
    }
}

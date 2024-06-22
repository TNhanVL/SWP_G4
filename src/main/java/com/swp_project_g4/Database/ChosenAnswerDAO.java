/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author TTNhan
 */
public class ChosenAnswerDAO extends DBConnection {

    public static boolean CheckChosenAnswer(int quizResultID, int selected_answer) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select 1 from ChosenAnswer where quizResultId = ? and answerId = ?");
            statement.setInt(1, quizResultID);
            statement.setInt(2, selected_answer);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }

            //disconnect to database
            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LearnerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return result
        return false;
    }

    public static void main(String[] args) {
//        deleteChosenAnswerOfQuestion(1, 3);
    }
}

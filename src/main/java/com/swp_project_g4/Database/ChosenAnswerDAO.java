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
 *
 * @author Gr4
 */
public class ChosenAnswerDAO extends DBConnection {

    public static boolean CheckChosenAnswer(int quizResultID, int questionID, int selectedAnswer) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select 1 from chosenAnswer where quizResultID = ? and questionID = ? and selectedAnswer = ?");
            statement.setInt(1, quizResultID);
            statement.setInt(2, questionID);
            statement.setInt(3, selectedAnswer);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }

            //disconnect to database
            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return result
        return false;
    }

    public static boolean CheckChosenAnswerCorrect(int quizResultID, int questionID) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select 1 from\n"
                    + "(select selectedAnswer as ID from chosenAnswer where quizResultID = ? and questionID = ?) a\n"
                    + "full join\n"
                    + "(select answerID from answer where questionID = ? and correct = 1) b\n"
                    + "on a.ID = b.answerID\n"
                    + "where a.ID is null or b.answerID is null");
            statement.setInt(1, quizResultID);
            statement.setInt(2, questionID);
            statement.setInt(3, questionID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return false;
            }

            //disconnect to database
            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return result
        return true;
    }

    

    public static void main(String[] args) {
        deleteChosenAnswerOfQuestion(1, 3);
    }
}

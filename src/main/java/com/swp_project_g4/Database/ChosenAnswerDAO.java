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

    public static boolean CheckChosenAnswer(int quizResultID, int questionId, int selected_answer) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select 1 from ChosenAnswer where quizResultId = ? and questionId = ? and answerId = ?");
            statement.setInt(1, quizResultID);
            statement.setInt(2, questionId);
            statement.setInt(3, selected_answer);
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

    public static boolean CheckChosenAnswerCorrect(int quizResultID, int questionId) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select 1 from\n"
                    + "(select answerId as ID from ChosenAnswer where quizResultId = ? and questionId = ?) a\n"
                    + "full join\n"
                    + "(select answerId from answer where questionId = ? and correct = 1) b\n"
                    + "on a.ID = b.answerId\n"
                    + "where a.ID is null or b.answerId is null");
            statement.setInt(1, quizResultID);
            statement.setInt(2, questionId);
            statement.setInt(3, questionId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return false;
            }

            //disconnect to database
            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LearnerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return result
        return true;
    }

    public static boolean insertChosenAnswer(int quizResultID, int questionId, int selected_answer) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("insert into ChosenAnswer(quizResultId,questionId,answerId,correct) values(?,?,?,?)");
            statement.setInt(1, quizResultID);
            statement.setInt(2, questionId);
            statement.setInt(3, selected_answer);
            statement.setBoolean(4, false);
            statement.execute();

            //disconnect to database
            disconnect();

            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    public static void deleteChosenAnswerOfQuestion(int quizResultID, int questionId) {
        try {
            connect();
            statement = conn.prepareStatement("delete from ChosenAnswer where quizResultId = ? and questionId = ?");
            statement.setInt(1, quizResultID);
            statement.setInt(2, questionId);
            statement.execute();
            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        deleteChosenAnswerOfQuestion(1, 3);
    }
}

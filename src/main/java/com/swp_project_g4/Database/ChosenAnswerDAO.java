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

    public static boolean CheckChosenAnswer(int quizResultID, int questionID, int selected_answer) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select 1 from chosen_answer where quizResultId = ? and questionID = ? and answerID = ?");
            statement.setInt(1, quizResultID);
            statement.setInt(2, questionID);
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

    public static boolean CheckChosenAnswerCorrect(int quizResultID, int questionID) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select 1 from\n"
                    + "(select answerID as ID from chosen_answer where quizResultId = ? and questionID = ?) a\n"
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
            Logger.getLogger(LearnerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return result
        return true;
    }

    public static boolean insertChosenAnswer(int quizResultID, int questionID, int selected_answer) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("insert into chosen_answer(quizResultId,questionID,answerID,correct) values(?,?,?,?)");
            statement.setInt(1, quizResultID);
            statement.setInt(2, questionID);
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

    public static void deleteChosenAnswerOfQuestion(int quizResultID, int questionID) {
        try {
            connect();
            statement = conn.prepareStatement("delete from chosen_answer where quizResultId = ? and questionID = ?");
            statement.setInt(1, quizResultID);
            statement.setInt(2, questionID);
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

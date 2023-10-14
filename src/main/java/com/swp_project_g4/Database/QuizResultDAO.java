/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Database;

import com.swp_project_g4.Model.Question;
import com.swp_project_g4.Model.QuizResult;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thanh Duong
 */
public class QuizResultDAO extends DBConnection {

    public static boolean existQuizResult(int ID) {
        boolean ok = false;
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select ID from quizResult where ID = ?");
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

    public static QuizResult getQuizResult(int ID) {
        QuizResult quizResult = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from quizResult where ID = ?");
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                quizResult = new QuizResult(
                        resultSet.getInt("ID"),
                        resultSet.getInt("lessonID"),
                        resultSet.getInt("userID"),
                        resultSet.getTimestamp("startTime"),
                        resultSet.getTimestamp("endTime")
                );
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return quizResult;
    }

    public static int getQuizResultPoint(int ID) {
        QuizResult quizResult = getQuizResult(ID);

        if (quizResult == null) {
            return 0;
        }

        ArrayList<Question> questions = QuestionDAO.getQuestionByLessonID(quizResult.getLessonID());

        int point = 0;

        for (Question question : questions) {
            if (ChosenAnswerDAO.CheckChosenAnswerCorrect(quizResult.getID(), question.getQuestionID())) {
                point++;
            }
        }

        return point;
    }

    public static QuizResult getLastQuizResult(int userID, int lessonID) {
        QuizResult quizResult = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select top 1 * from quizResult where userID = ? and lessonID = ? order by startTime desc");
            statement.setInt(1, userID);
            statement.setInt(2, lessonID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                quizResult = new QuizResult(
                        resultSet.getInt("ID"),
                        resultSet.getInt("lessonID"),
                        resultSet.getInt("userID"),
                        resultSet.getTimestamp("startTime"),
                        resultSet.getTimestamp("endTime")
                );
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return quizResult;
    }

   
    public static void main(String[] args) {

    }
}

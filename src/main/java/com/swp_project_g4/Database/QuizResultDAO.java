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
 * @author TTNhan
 */
public class QuizResultDAO extends DBConnection {

    public static boolean existQuizResult(int quizResultID) {
        boolean ok = false;
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select quiz_resultID from quiz_result where quiz_resultID = ?");
            statement.setInt(1, quizResultID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getInt("quiz_resultID") == quizResultID) {
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

    public static QuizResult getQuizResult(int quizResultID) {
        QuizResult quizResult = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from quiz_result where quiz_resultID = ?");
            statement.setInt(1, quizResultID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                quizResult = new QuizResult(
                        resultSet.getInt("quiz_resultID"),
                        resultSet.getInt("lessonID"),
                        resultSet.getInt("userID"),
                        resultSet.getTimestamp("start_at"),
                        resultSet.getTimestamp("end_at")
                );
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return quizResult;
    }

    public static int getQuizResultPoint(int quizResultID) {
        QuizResult quizResult = getQuizResult(quizResultID);

        if (quizResult == null) {
            return 0;
        }

        ArrayList<Question> questions = QuestionDAO.getQuestionByLessonID(quizResult.getLessonID());

        int point = 0;

        for (Question question : questions) {
            if (ChosenAnswerDAO.CheckChosenAnswerCorrect(quizResult.getQuizResultID(), question.getQuestionID())) {
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

            statement = conn.prepareStatement("select top 1 * from quiz_result where userID = ? and lessonID = ? order by start_at desc");
            statement.setInt(1, userID);
            statement.setInt(2, lessonID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                quizResult = new QuizResult(
                        resultSet.getInt("quiz_resultID"),
                        resultSet.getInt("lessonID"),
                        resultSet.getInt("userID"),
                        resultSet.getTimestamp("start_at"),
                        resultSet.getTimestamp("end_at")
                );
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return quizResult;
    }

    public static int insertQuizResult(QuizResult quizResult) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("insert into quiz_result(lessonID,userID,start_at,end_at) values(?,?,?,?)");
            statement.setInt(1, quizResult.getLessonID());
            statement.setInt(2, quizResult.getUserID());
            statement.setString(3, dateFormat.format(quizResult.getStartAt()));
            statement.setString(4, dateFormat.format(quizResult.getEndAt()));
            statement.executeUpdate();

            int newID = lastModifyID(conn);

            //disconnect to database
            disconnect();

            return newID;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);

        }
        return -1;
    }

    public static boolean updateQuizResult(QuizResult quizResult) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("update quiz_result set lessonID=?,userID=?, start_at=?, end_at=? where quiz_resultID=?");
            statement.setInt(1, quizResult.getLessonID());
            statement.setInt(2, quizResult.getUserID());
            statement.setString(3, dateFormat.format(quizResult.getStartAt()));
            statement.setString(4, dateFormat.format(quizResult.getEndAt()));
            statement.setInt(5, quizResult.getQuizResultID());
            statement.executeUpdate();

            //disconnect to database
            disconnect();
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean deleteQuizResult(int quizResultID) {
        try {
            if (!existQuizResult(quizResultID)) {
                return false;
            }
            connect();
            statement = conn.prepareStatement("delete from quizResult where quizResultID=?");
            statement.setInt(1, quizResultID);
            statement.execute();
            disconnect();
            return !existQuizResult(quizResultID);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void main(String[] args) {

    }
}

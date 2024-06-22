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

//    public static boolean existQuizResult(int quizResultID) {
//        boolean ok = false;
//        try {
//            //connect to database
//            connect();
//
//            statement = conn.prepareStatement("select quizResultId from QuizResult where quizResultId = ?");
//            statement.setInt(1, quizResultID);
//            ResultSet resultSet = statement.executeQuery();
//
//            if (resultSet.next()) {
//                if (resultSet.getInt("quizResultId") == quizResultID) {
//                    ok = true;
//                }
//            }
//
//            //disconnect to database
//            disconnect();
//        } catch (SQLException | ClassNotFoundException ex) {
//            Logger.getLogger(LearnerDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        //return result
//        return ok;
//    }

    public static QuizResult getQuizResult(int quizResultID) {
        QuizResult quizResult = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from QuizResult where quizResultId = ?");
            statement.setInt(1, quizResultID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                quizResult = new QuizResult(
                        resultSet.getInt("quizResultId"),
                        resultSet.getInt("lessonId"),
                        resultSet.getInt("lessonProgressId"),
                        resultSet.getInt("numberOfCorrectAnswer"),
                        resultSet.getInt("numberOfQuestion"),
                        resultSet.getInt("mark"),
                        resultSet.getTimestamp("startAt"),
                        resultSet.getTimestamp("endAt")
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

        ArrayList<Question> questions = QuestionDAO.getQuestionByLessonId(quizResult.getLessonId());

        int point = 0;

        for (Question question : questions) {
            if (ChosenAnswerDAO.CheckChosenAnswerCorrect(quizResult.getID(), question.getID())) {
                point++;
            }
        }

        return point;
    }

//    public static QuizResult getLastQuizResult(int userId, int lessonId) {
//        QuizResult quizResult = null;
//
//        try {
//            //connect to database
//            connect();
//
//            statement = conn.prepareStatement("select top 1 * from QuizResult where learnerId = ? and lessonId = ? order by startAt desc");
//            statement.setInt(1, userId);
//            statement.setInt(2, lessonId);
//            ResultSet resultSet = statement.executeQuery();
//
//            if (resultSet.next()) {
//                quizResult = new QuizResult(
//                        resultSet.getInt("quizResultId"),
//                        resultSet.getInt("lessonId"),
//                        resultSet.getInt("lessonProgressId"),
//                        resultSet.getInt("numberOfCorrectAnswer"),
//                        resultSet.getInt("numberOfQuestion"),
//                        resultSet.getInt("mark"),
//                        resultSet.getTimestamp("startAt"),
//                        resultSet.getTimestamp("endAt")
//                );
//            }
//
//            disconnect();
//        } catch (SQLException | ClassNotFoundException ex) {
//            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return quizResult;
//    }

//    public static int insertQuizResult(QuizResult quizResult) {
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//
//        try {
//            //connect to database
//            connect();
//
//            statement = conn.prepareStatement("insert into QuizResult(lessonId,startAt,endAt) values(?,?,?)");
//            statement.setInt(1, quizResult.getLessonId());
//            statement.setString(2, dateFormat.format(quizResult.getStartAt()));
//            statement.setString(3, dateFormat.format(quizResult.getEndAt()));
//            statement.executeUpdate();
//
//            int newID = lastModifyID(conn);
//
//            //disconnect to database
//            disconnect();
//
//            return newID;
//
//        } catch (SQLException | ClassNotFoundException ex) {
//            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
//
//        }
//        return -1;
//    }

//    public static boolean updateQuizResult(QuizResult quizResult) {
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//
//        try {
//            //connect to database
//            connect();
//
//            statement = conn.prepareStatement("update QuizResult set lessonId=?, startAt=?, endAt=? where quizResultId=?");
//            statement.setInt(1, quizResult.getLessonId());
//            statement.setString(2, dateFormat.format(quizResult.getStartAt()));
//            statement.setString(3, dateFormat.format(quizResult.getEndAt()));
//            statement.setInt(4, quizResult.getID());
//            statement.executeUpdate();
//
//            //disconnect to database
//            disconnect();
//            return true;
//
//        } catch (SQLException | ClassNotFoundException ex) {
//            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return false;
//    }

//    public static boolean deleteQuizResult(int quizResultID) {
//        try {
//            if (!existQuizResult(quizResultID)) {
//                return false;
//            }
//            connect();
//            statement = conn.prepareStatement("delete from QuizResult where quizResultId=?");
//            statement.setInt(1, quizResultID);
//            statement.execute();
//            disconnect();
//            return !existQuizResult(quizResultID);
//        } catch (SQLException | ClassNotFoundException ex) {
//            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return false;
//    }

    public static void main(String[] args) {

    }
}

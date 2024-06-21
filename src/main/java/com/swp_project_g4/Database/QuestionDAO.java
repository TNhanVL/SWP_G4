/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Database;

import com.swp_project_g4.Model.Question;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author TTNhan
 */
public class QuestionDAO extends DBConnection {

    public static boolean existQuestion(int questionId) {
        boolean ok = false;
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select questionId from question where questionId = ?");
            statement.setInt(1, questionId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getInt("questionId") == questionId) {
                    ok = true;
                }
            }

            //disconnect to database
            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LearnerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return result
        return ok;
    }

    public static Question getQuestion(int questionId) {
        Question question = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from question where questionId = ?");
            statement.setInt(1, questionId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                question = new Question(
                        resultSet.getInt("questionId"),
                        resultSet.getInt("lessonId"),
                        resultSet.getInt("index"),
                        resultSet.getString("content"),
                        resultSet.getInt("type"),
                        resultSet.getInt("point")
                );
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return question;
    }

    public static ArrayList<Question> getQuestionByLessonId(int lessonId) {
        ArrayList<Question> questions = new ArrayList<>();

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from question where lessonId = ? order by [index]");
            statement.setInt(1, lessonId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Question question = new Question(
                        resultSet.getInt("questionId"),
                        resultSet.getInt("lessonId"),
                        resultSet.getInt("index"),
                        resultSet.getString("content"),
                        resultSet.getInt("type"),
                        resultSet.getInt("point")
                );
                questions.add(question);
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return questions;
    }

    public static int getNumberQuestionByLessonId(int lessonId) {
        int ans = 0;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select count(*) as number from question where lessonId = ?");
            statement.setInt(1, lessonId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("number");
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ans;
    }

    public static boolean insertQuestion(Question question) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("insert into question(lessonId,[index],content,[type],point) values (?,?,?,?,?)");
            statement.setInt(1, question.getLessonId());
            statement.setInt(2, question.getIndex());
            statement.setString(3, question.getContent());
            statement.setInt(4, question.getType());
            statement.setInt(5, question.getPoint());
            statement.executeUpdate();
            //disconnect to database
            disconnect();
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    public static boolean updateQuestion(Question question) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("update question set lessonId=?, [index]=?, content=?, [type]=?, point=? where questionId=?");
            statement.setInt(1, question.getLessonId());
            statement.setInt(2, question.getIndex());
            statement.setString(3, question.getContent());
            statement.setInt(4, question.getType());
            statement.setInt(5, question.getPoint());
            statement.setInt(6, question.getID());

            //disconnect to database
            disconnect();
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean deleteQuestion(int questionId) {
        try {
            if (!existQuestion(questionId)) {
                return false;
            }
            connect();
            statement = conn.prepareStatement("delete from question where questionId=?");
            statement.setInt(1, questionId);
            statement.execute();
            disconnect();
            return !existQuestion(questionId);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(getNumberQuestionByLessonId(2));
    }
}

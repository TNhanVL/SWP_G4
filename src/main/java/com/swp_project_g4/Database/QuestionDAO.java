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

    public static boolean existQuestion(int questionID) {
        boolean ok = false;
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select questionID from question where questionID = ?");
            statement.setInt(1, questionID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getInt("questionID") == questionID) {
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

    public static Question getQuestion(int questionID) {
        Question question = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from question where questionID = ?");
            statement.setInt(1, questionID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                question = new Question(
                        resultSet.getInt("questionID"),
                        resultSet.getInt("lessonID"),
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

    public static ArrayList<Question> getQuestionByLessonID(int lessonID) {
        ArrayList<Question> questions = new ArrayList<>();

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from question where lessonID = ? order by [index]");
            statement.setInt(1, lessonID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Question question = new Question(
                        resultSet.getInt("questionID"),
                        resultSet.getInt("lessonID"),
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

    public static int getNumberQuestionByLessonID(int lessonID) {
        int ans = 0;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select count(*) as number from question where lessonID = ?");
            statement.setInt(1, lessonID);
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

            statement = conn.prepareStatement("insert into question(lessonID,[index],content,[type],point) values (?,?,?,?,?)");
            statement.setInt(1, question.getLessonID());
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

            statement = conn.prepareStatement("update question set lessonID=?, [index]=?, content=?, [type]=?, point=? where questionID=?");
            statement.setInt(1, question.getLessonID());
            statement.setInt(2, question.getIndex());
            statement.setString(3, question.getContent());
            statement.setInt(4, question.getType());
            statement.setInt(5, question.getPoint());
            statement.setInt(6, question.getQuestionID());

            //disconnect to database
            disconnect();
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean deleteQuestion(int questionID) {
        try {
            if (!existQuestion(questionID)) {
                return false;
            }
            connect();
            statement = conn.prepareStatement("delete from question where questionID=?");
            statement.setInt(1, questionID);
            statement.execute();
            disconnect();
            if (!existQuestion(questionID)) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(getNumberQuestionByLessonID(2));
    }
}

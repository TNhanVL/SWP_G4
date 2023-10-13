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
 *
 * @author Thanh Duong
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
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
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

    

    public static void main(String[] args) {
        System.out.println(getNumberQuestionByLessonID(2));
    }
}

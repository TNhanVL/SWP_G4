/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Database;

import com.swp_project_g4.Model.Answer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author TTNhan
 */
public class AnswerDAO extends DBConnection {

    public static boolean existAnswer(int answerId) {
        boolean ok = false;
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select answerId from answer where answerId = ?");
            statement.setInt(1, answerId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getInt("answerId") == answerId) {
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

    public static Answer getAnswer(int answerId) {
        Answer answer = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from answer where answerId = ?");
            statement.setInt(1, answerId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                answer = new Answer(
                        resultSet.getInt("answerId"),
                        resultSet.getString("content"),
                        resultSet.getBoolean("correct"),
                        resultSet.getInt("questionId")
                );
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return answer;
    }

    public static ArrayList<Answer> getAnswersByQuestionId(int questionId) {
        ArrayList<Answer> answers = new ArrayList<>();

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from answer where questionId = ?");
            statement.setInt(1, questionId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Answer answer = new Answer(
                        resultSet.getInt("answerId"),
                        resultSet.getString("content"),
                        resultSet.getBoolean("correct"),
                        resultSet.getInt("questionId")
                );
                answers.add(answer);
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return answers;
    }

    public static boolean insertAnswer(Answer answer) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("insert into answer(content,correct,questionId) values (?,?,?)");
            statement.setString(1, answer.getContent());
            statement.setBoolean(2, answer.isCorrect());
            statement.setInt(3, answer.getQuestionId());
            statement.executeUpdate();
            //disconnect to database
            disconnect();
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    public static boolean updateAnswer(Answer answer) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("update answer set content=?, correct=?, questionId=? where answerId=?");
            statement.setString(1, answer.getContent());
            statement.setBoolean(2, answer.isCorrect());
            statement.setInt(3, answer.getQuestionId());
            statement.setInt(4, answer.getID());

            //disconnect to database
            disconnect();
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean deleteAnswer(int answerId) {
        try {
            if (!existAnswer(answerId)) {
                return false;
            }
            connect();
            statement = conn.prepareStatement("delete from answer where answerId=?");
            statement.setInt(1, answerId);
            statement.execute();
            disconnect();
            return !existAnswer(answerId);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(getAnswersByQuestionId(1));
    }
}

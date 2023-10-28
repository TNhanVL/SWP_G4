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

    public static boolean existAnswer(int answerID) {
        boolean ok = false;
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select answerID from answer where answerID = ?");
            statement.setInt(1, answerID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getInt("answerID") == answerID) {
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

    public static Answer getAnswer(int answerID) {
        Answer answer = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from answer where answerID = ?");
            statement.setInt(1, answerID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                answer = new Answer(
                        resultSet.getInt("answerID"),
                        resultSet.getString("content"),
                        resultSet.getBoolean("correct"),
                        resultSet.getInt("questionID")
                );
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return answer;
    }

    public static ArrayList<Answer> getAnswersByQuestionID(int questionID) {
        ArrayList<Answer> answers = new ArrayList<>();

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from answer where questionID = ?");
            statement.setInt(1, questionID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Answer answer = new Answer(
                        resultSet.getInt("answerID"),
                        resultSet.getString("content"),
                        resultSet.getBoolean("correct"),
                        resultSet.getInt("questionID")
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

            statement = conn.prepareStatement("insert into answer(content,correct,questionID) values (?,?,?)");
            statement.setString(1, answer.getContent());
            statement.setBoolean(2, answer.getCorrect());
            statement.setInt(3, answer.getQuestionID());
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

            statement = conn.prepareStatement("update answer set content=?, correct=?, questionID=? where answerID=?");
            statement.setString(1, answer.getContent());
            statement.setBoolean(2, answer.getCorrect());
            statement.setInt(3, answer.getQuestionID());
            statement.setInt(4, answer.getID());

            //disconnect to database
            disconnect();
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean deleteAnswer(int answerID) {
        try {
            if (!existAnswer(answerID)) {
                return false;
            }
            connect();
            statement = conn.prepareStatement("delete from answer where answerID=?");
            statement.setInt(1, answerID);
            statement.execute();
            disconnect();
            return !existAnswer(answerID);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(getAnswersByQuestionID(1));
    }
}

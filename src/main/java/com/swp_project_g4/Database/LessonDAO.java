/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Database;

import com.swp_project_g4.Model.*;
import com.swp_project_g4.Service.Certificate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.swp_project_g4.Service.EmailService;
import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author TTNhan
 */
public class LessonDAO extends DBConnection {

    public static Lesson getLesson(int lessonId) {
        Lesson lesson = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from lesson where lessonId = ?");
            statement.setInt(1, lessonId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                lesson = new Lesson(
                        resultSet.getInt("lessonId"),
                        resultSet.getInt("ChapterId"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("percentToPassed"),
                        resultSet.getBoolean("mustBeCompleted"),
                        resultSet.getString("content"),
                        resultSet.getInt("type"),
                        resultSet.getInt("index"),
                        resultSet.getInt("time"));
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lesson;
    }

    public static ArrayList<Lesson> getLessonsByChapterId(int chapterId) {
        ArrayList<Lesson> lessons = new ArrayList<>();

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from lesson where chapterId = ? order by [index]");
            statement.setInt(1, chapterId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Lesson lesson = new Lesson(
                        resultSet.getInt("lessonId"),
                        resultSet.getInt("ChapterId"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("percentToPassed"),
                        resultSet.getBoolean("mustBeCompleted"),
                        resultSet.getString("content"),
                        resultSet.getInt("type"),
                        resultSet.getInt("index"),
                        resultSet.getInt("time"));
                lessons.add(lesson);
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lessons;
    }

    public static int getNumberLessonsByChapterId(int chapterId) {
        int ans = 0;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select count(*) as number from lesson where chapterId = ?");
            statement.setInt(1, chapterId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                ans = resultSet.getInt("number");
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ans;
    }

    public static void main(String[] args) {
        System.out.println(getNumberLessonsByChapterId(1));
    }
}

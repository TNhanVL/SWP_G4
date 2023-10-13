/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Database;

import com.swp_project_g4.Model.Lesson;
import com.swp_project_g4.Model.Chapter;
import com.swp_project_g4.Model.QuizResult;
import com.swp_project_g4.Service.Certificate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author Thanh Duong
 */
public class LessonDAO extends DBConnection {

    public static boolean existLesson(int ID) {
        boolean ok = false;
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select ID from lesson where ID = ?");
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

    public static Lesson getLesson(int ID) {
        Lesson lesson = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from lesson where ID = ?");
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                lesson = new Lesson(
                        resultSet.getInt("ID"),
                        resultSet.getInt("ChapterID"),
                        resultSet.getString("name"),
                        resultSet.getInt("index"),
                        resultSet.getInt("type"),
                        resultSet.getInt("time"));
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lesson;
    }

    public static boolean checkLessonCompleted(int userID, int lessonID, HttpServletRequest request) {
        boolean ok = false;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select 1 from lessonCompleted where lessonID = ? and userID = ?");
            statement.setInt(1, lessonID);
            statement.setInt(2, userID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                ok = true;
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        //if not completed, check if quiz not judge yet
        Lesson lesson = LessonDAO.getLesson(lessonID);
        if (!ok) {
            if (lesson.getType() == 2) {
                QuizResult quizResult = QuizResultDAO.getLastQuizResult(userID, lessonID);
                //if not take quiz yet or not finished yet
                if (quizResult == null || quizResult.getEndTime().after(new Date())) {
                    return false;
                }
                int numberOfCorrectQuestion = QuizResultDAO.getQuizResultPoint(quizResult.getID());
                int numberOfQuestion = QuestionDAO.getNumberQuestionByLessonID(lessonID);
                if (numberOfCorrectQuestion * 100 >= numberOfQuestion * 80) {
                    LessonDAO.insertLessonCompleted(userID, lessonID, request);
                    return true;
                }
            }
        }

        return ok;
    }

    public static int getNumberLessonsCompleted(int userID, int chapterID) {
        int ans = 0;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select count(*) as number from\n"
                    + "(select lessonID as ID from lessonCompleted where userID = ?) as a\n"
                    + "join\n"
                    + "(select ID from lesson where chapterID = ?) as b\n"
                    + "on a.ID = b.ID");
            statement.setInt(1, userID);
            statement.setInt(2, chapterID);
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

    public static boolean insertLessonCompleted(int userID, int lessonID, HttpServletRequest request) {
//        deleteLessonCompleted(userID, lessonID);
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("insert into lessonCompleted(lessonID, userID) values(?,?)");
            statement.setInt(1, lessonID);
            statement.setInt(2, userID);
            statement.executeUpdate();
            //disconnect to database
            disconnect();

            Lesson lesson = LessonDAO.getLesson(lessonID);
            Chapter chapter = ChapterDAO.getChapter(lesson.getChapterID());
            // Generate new certificate if completed course
            if (CourseDAO.checkCourseCompleted(userID, chapter.getCourseID())) {
                // if completed course
                String certificateName = "certificate_" + chapter.getCourseID() + "_" + userID + ".pdf";
                CourseDAO.insertCertificate(userID, chapter.getCourseID(), certificateName);
                Certificate.createCertificate(certificateName, userID, chapter.getCourseID(), request);
                
//                User user = UserDAO.getUser(userID);
//                EmailService.sendCompletecourse(user.getEmail(), "http://localhost:8080/swp_project_g4/public/media/certificate/" + certificateName);
            }

            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    

    
    

    

    

    

    
    

    public static void main(String[] args) {
        System.out.println(getNumberLessonsByChapterID(1));
    }
}

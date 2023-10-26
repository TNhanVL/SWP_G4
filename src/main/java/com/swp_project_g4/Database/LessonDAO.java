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

    public static boolean existLesson(int lessonID) {
        boolean ok = false;
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select lessonID from lesson where lessonID = ?");
            statement.setInt(1, lessonID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getInt("lessonID") == lessonID) {
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

    public static Lesson getLesson(int lessonID) {
        Lesson lesson = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from lesson where lessonID = ?");
            statement.setInt(1, lessonID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                lesson = new Lesson(
                        resultSet.getInt("lessonID"),
                        resultSet.getInt("ChapterID"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("percent_to_passed"),
                        resultSet.getBoolean("must_be_completed"),
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

    public static boolean checkLessonCompleted(int userID, int lessonID, HttpServletRequest request) {
        boolean ok = false;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select 1 from lesson_completed where lessonID = ? and userID = ?");
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
                if (quizResult == null || quizResult.getEndAt().after(new Date())) {
                    return false;
                }
                int numberOfCorrectQuestion = QuizResultDAO.getQuizResultPoint(quizResult.getQuizResultID());
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
                    + "(select lessonID from lesson_completed where userID = ?) as a\n"
                    + "join\n"
                    + "(select lessonID from lesson where chapterID = ?) as b\n"
                    + "on a.lessonID = b.lessonID");
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

            statement = conn.prepareStatement("insert into lesson_completed(lessonID, userID) values(?,?)");
            statement.setInt(1, lessonID);
            statement.setInt(2, userID);
            statement.executeUpdate();
            //disconnect to database
            disconnect();

            Lesson lesson = LessonDAO.getLesson(lessonID);
            Chapter chapter = ChapterDAO.getChapter(lesson.getChapterID());
            // Generate new certificate if completed course
            if (CourseDAO.markCourseCompleted(userID, chapter.getCourseID())) {
                // if completed course
                String certificateName = "certificate_" + chapter.getCourseID() + "_" + userID + ".pdf";
                CourseDAO.insertCertificate(userID, chapter.getCourseID(), certificateName);
                Certificate.createCertificate(certificateName, userID, chapter.getCourseID(), request);
                
                User user = UserDAO.getUser(userID);
                Course course = CourseDAO.getCourse(chapter.getCourseID());
                EmailService.sendCompletecourse(user, course, "http://localhost:8080/public/media/certificate/" + certificateName);
            }

            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    public static int getFirstUncompleteLessonID(int userID, int courseID) {
        int lessonID = -1;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select top 1 lessonID from\n"
                    + "(select chapterIndex, lessonID, lessonIndex from\n"
                    + "(select chapterID, [index] as chapterIndex from chapter where courseID = ?) as a\n"
                    + "join\n"
                    + "(select chapterID, lessonID, [index] as lessonIndex from lesson) as b on a.chapterID = b.chapterID) a\n"
                    + "where lessonID not in\n"
                    + "(select lessonID from lesson_completed where userID = ?)\n"
                    + "order by chapterIndex, lessonIndex;");
            statement.setInt(1, courseID);
            statement.setInt(2, userID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                lessonID = resultSet.getInt("lessonID");
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (lessonID < 0) {
            lessonID = getLastLessonID(courseID);
        }

        return lessonID;
    }

    public static int getLastLessonID(int courseID) {
        int lessonID = -1;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select top 1 lessonID from\n"
                    + "(select chapterID, [index] as chapterIndex from chapter where courseID = ?) as a\n"
                    + "join\n"
                    + "(select chapterID, lessonID, [index] as lessonIndex from lesson) as b on a.chapterID = b.chapterID\n"
                    + "order by chapterIndex desc, lessonIndex desc");
            statement.setInt(1, courseID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                lessonID = resultSet.getInt("lessonID");
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lessonID;
    }

    public static ArrayList<Lesson> getLessonsByChapterID(int chapterID) {
        ArrayList<Lesson> lessons = new ArrayList<>();

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from lesson where chapterID = ? order by [index]");
            statement.setInt(1, chapterID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Lesson lesson = new Lesson(
                        resultSet.getInt("lessonID"),
                        resultSet.getInt("ChapterID"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("percent_to_passed"),
                        resultSet.getBoolean("must_be_completed"),
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

    public static int getNumberLessonsByChapterID(int chapterID) {
        int ans = 0;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select count(*) as number from lesson where chapterID = ?");
            statement.setInt(1, chapterID);
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

    public static boolean insertLesson(Lesson lesson) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("insert into lesson(chapterID,name,[index],[type],[time]) values(?,?,?,?,?)");
            statement.setInt(1, lesson.getChapterID());
            statement.setString(2, lesson.getName());
            statement.setInt(3, lesson.getIndex());
            statement.setInt(4, lesson.getType());
            statement.setInt(5, lesson.getTime());
            statement.executeUpdate();
            //disconnect to database
            disconnect();
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    public static boolean updateLesson(Lesson lesson) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("update lesson set chapterID=?, name=?, [index]=?, type=?, [time]=? where lessonID=?");
            statement.setInt(1, lesson.getChapterID());
            statement.setString(2, lesson.getName());
            statement.setInt(3, lesson.getIndex());
            statement.setInt(4, lesson.getType());
            statement.setInt(5, lesson.getTime());
            statement.setInt(6, lesson.getLessonID());
            statement.executeUpdate();

            //disconnect to database
            disconnect();
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean deleteLesson(int lessonID) {
        try {
            if (!existLesson(lessonID)) {
                return false;
            }
            connect();
            statement = conn.prepareStatement("delete from lesson where lessonID=?");
            statement.setInt(1, lessonID);
            statement.execute();
            disconnect();
            if (!existLesson(lessonID)) {
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
        System.out.println(getNumberLessonsByChapterID(1));
    }
}

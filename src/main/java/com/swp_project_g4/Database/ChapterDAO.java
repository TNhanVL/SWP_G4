/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Database;

import com.swp_project_g4.Model.Chapter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author TTNhan
 */
public class ChapterDAO extends DBConnection {

    public static boolean existChapter(int chapterId) {
        boolean ok = false;
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select chapterId from chapter where chapterId = ?");
            statement.setInt(1, chapterId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getInt("chapterId") == chapterId) {
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

    public static Chapter getChapter(int chapterId) {
        Chapter chapter = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from chapter where chapterId = ?");
            statement.setInt(1, chapterId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                chapter = new Chapter(
                        resultSet.getInt("chapterId"),
                        resultSet.getInt("courseId"),
                        resultSet.getInt("index"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("totalTime"));
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return chapter;
    }

    public static ArrayList<Chapter> getChaptersByCourseId(int courseId) {
        ArrayList<Chapter> chapters = new ArrayList<>();

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from chapter where courseId = ? order by [index]");
            statement.setInt(1, courseId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Chapter chapter = new Chapter(
                        resultSet.getInt("chapterId"),
                        resultSet.getInt("courseId"),
                        resultSet.getInt("index"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("totalTime"));
                chapters.add(chapter);
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return chapters;
    }

    public static boolean insertChapter(Chapter chapter) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("insert into chapter(courseId,[index],name,description) values(?,?,?,?)");
            statement.setInt(1, chapter.getCourseId());
            statement.setInt(2, chapter.getIndex());
            statement.setString(3, chapter.getName());
            statement.setString(4, chapter.getDescription());
            statement.executeUpdate();

            //disconnect to database
            disconnect();
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    public static boolean updateChapter(Chapter chapter) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("update chapter set courseId=?, [index]=?, name=?, description=? where chapterId=?");
            statement.setInt(1, chapter.getCourseId());
            statement.setInt(2, chapter.getIndex());
            statement.setString(3, chapter.getName());
            statement.setString(4, chapter.getDescription());
            statement.setInt(5, chapter.getID());
            statement.executeUpdate();

            //disconnect to database
            disconnect();
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean deleteChapter(int chapterId) {
        try {
            if (!existChapter(chapterId)) {
                return false;
            }
            connect();
            statement = conn.prepareStatement("delete from chapter where chapterId=?");
            statement.setInt(1, chapterId);
            statement.execute();
            disconnect();
            return !existChapter(chapterId);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(getChaptersByCourseId(1));
    }
}

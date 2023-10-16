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
 *
 * @author Thanh Duong
 */
public class ChapterDAO extends DBConnection {

    public static boolean existChapter(int chapterID) {
        boolean ok = false;
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select chapterID from chapter where chapterID = ?");
            statement.setInt(1, chapterID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getInt("chapterID") == chapterID) {
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

    public static Chapter getChapter(int chapterID) {
        Chapter chapter = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from chapter where chapterID = ?");
            statement.setInt(1, chapterID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                chapter = new Chapter(
                        resultSet.getInt("chapterID"),
                        resultSet.getInt("courseID"),
                        resultSet.getInt("index"),
                        resultSet.getString("name"),
                        resultSet.getString("description"));
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return chapter;
    }

    public static ArrayList<Chapter> getChaptersByCourseID(int courseID) {
        ArrayList<Chapter> chapters = new ArrayList<>();

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from chapter where courseID = ? order by [index]");
            statement.setInt(1, courseID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Chapter chapter = new Chapter(
                        resultSet.getInt("chapterID"),
                        resultSet.getInt("courseID"),
                        resultSet.getInt("index"),
                        resultSet.getString("name"),
                        resultSet.getString("description"));
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

            statement = conn.prepareStatement("insert into chapter(courseID,[index],name,description) values(?,?,?,?)");
            statement.setInt(1, chapter.getCourseID());
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

            statement = conn.prepareStatement("update chapter set courseID=?, [index]=?, name=?, description=? where chapterID=?");
            statement.setInt(1, chapter.getCourseID());
            statement.setInt(2, chapter.getIndex());
            statement.setString(3, chapter.getName());
            statement.setString(4, chapter.getDescription());
            statement.setInt(5, chapter.getChapterID());
            statement.executeUpdate();

            //disconnect to database
            disconnect();
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean deleteChapter(int chapterID) {
        try {
            if (!existChapter(chapterID)) {
                return false;
            }
            connect();
            statement = conn.prepareStatement("delete from chapter where chapterID=?");
            statement.setInt(1, chapterID);
            statement.execute();
            disconnect();
            if (!existChapter(chapterID)) {
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
        System.out.println(getChaptersByCourseID(1));
    }
}

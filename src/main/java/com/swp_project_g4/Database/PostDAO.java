/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Database;

import com.swp_project_g4.Model.Post;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thanh Duong
 */
public class PostDAO extends DBConnection {

    public static boolean existPost(int ID) {
        boolean ok = false;
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select ID from post where ID = ?");
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

    public static Post getPost(int ID) {
        Post post = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from post where ID = ?");
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                post = new Post(
                        resultSet.getInt("ID"),
                        resultSet.getString("content"),
                        resultSet.getInt("lessonID"));
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return post;
    }

    public static Post getPostByLessonID(int lessonID) {
        Post post = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from post where lessonID = ?");
            statement.setInt(1, lessonID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                post = new Post(
                        resultSet.getInt("ID"),
                        resultSet.getString("content"),
                        resultSet.getInt("lessonID"));
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return post;
    }

    public static boolean insertPost(Post post) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("insert into post(content,lessonID) values (?,?)");
            statement.setString(1, post.getContent());
            statement.setInt(2, post.getLessonID());
            statement.executeUpdate();
            //disconnect to database
            disconnect();
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    public static boolean updatePost(Post post) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("update post set content=?, lessonID=? where ID=?");
            statement.setString(1, post.getContent());
            statement.setInt(2, post.getLessonID());
            statement.setInt(3, post.getID());
            statement.executeUpdate();

            //disconnect to database
            disconnect();
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean deletePost(int ID) {
        try {
            if (!existPost(ID)) {
                return false;
            }
            connect();
            statement = conn.prepareStatement("delete from post where ID=?");
            statement.setInt(1, ID);
            statement.execute();
            disconnect();
            if (!existPost(ID)) {
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
//        System.out.println(getPost(1));
//        Post p = getPost(3);
//        p.setContent("Hmm...");
//        updatePost(p);
//        deletePost(3);

    }
}

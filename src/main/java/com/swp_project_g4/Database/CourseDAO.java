/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Database;

import com.swp_project_g4.Model.Course;
import com.swp_project_g4.Model.Chapter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author TTNhan
 */
public class CourseDAO extends DBConnection {

    public static boolean existCourse(int courseID) {
        boolean ok = false;
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select courseID from course where courseID = ?");
            statement.setInt(1, courseID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getInt("courseID") == courseID) {
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

    public static Course getCourse(int courseID) {
        Course course = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from course where courseID = ?");
            statement.setInt(1, courseID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                course = new Course(
                        resultSet.getInt("courseID"),
                        resultSet.getInt("organizationID"),
                        resultSet.getString("name"),
                        resultSet.getString("picture"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("verified"),
                        resultSet.getInt("total_time"),
                        resultSet.getDouble("price"),
                        resultSet.getDouble("rate")
                );
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return course;
    }

    public static ArrayList<Course> getAllCourses() {
        ArrayList<Course> courses = new ArrayList<>();

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from course");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Course course = new Course(
                        resultSet.getInt("courseID"),
                        resultSet.getInt("organizationID"),
                        resultSet.getString("name"),
                        resultSet.getString("picture"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("verified"),
                        resultSet.getInt("total_time"),
                        resultSet.getDouble("price"),
                        resultSet.getDouble("rate")
                );
                courses.add(course);
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return courses;
    }

    public static ArrayList<Course> searchCourses(String name) {
        ArrayList<Course> courses = new ArrayList<>();

        try {
            //connect to database
            connect();

            // search course by name
            String sql = "select * from course where name like ?";
            statement = conn.prepareStatement(sql);
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Course course = new Course(
                        resultSet.getInt("courseID"),
                        resultSet.getInt("organizationID"),
                        resultSet.getString("name"),
                        resultSet.getString("picture"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("verified"),
                        resultSet.getInt("total_time"),
                        resultSet.getDouble("price"),
                        resultSet.getDouble("rate")
                );
                courses.add(course);
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return courses;
    }

    public static ArrayList<Course> getPopularCourses(int limit) {
        ArrayList<Course> courses = new ArrayList<>();

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select top " + limit + " * from course");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Course course = new Course(
                        resultSet.getInt("courseID"),
                        resultSet.getInt("organizationID"),
                        resultSet.getString("name"),
                        resultSet.getString("picture"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("verified"),
                        resultSet.getInt("total_time"),
                        resultSet.getDouble("price"),
                        resultSet.getDouble("rate")
                );
                courses.add(course);
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return courses;
    }

    public static int getSumTimeOfCourse(int courseID) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("""
                    select sum([time]) as sumTime from
                    (select * from lesson) as l
                    join
                    (select * from chapter where courseID = ?) as m
                    on l.chapterID = m.chapterID""");
            statement.setInt(1, courseID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("sumTime");
            }

            //disconnect to database
            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LearnerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return result
        return 0;
    }

    public static int countCartProduct(int userID) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select count(*) as number from cart_product where userID = ?");
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("number");
            }

            //disconnect to database
            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LearnerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return result
        return 0;
    }

    public static boolean checkCartProduct(int userID, int courseID) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select 1 from cart_product where userID = ? and courseID = ?");
            statement.setInt(1, userID);
            statement.setInt(2, courseID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }

            //disconnect to database
            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LearnerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return result
        return false;
    }

    public static boolean insertCartProduct(int userID, int courseID) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("insert into cart_product(userID, courseID) values (?,?)");
            statement.setInt(1, userID);
            statement.setInt(2, courseID);

            statement.execute();

            //disconnect to database
            disconnect();
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    public static boolean deleteCartProduct(int userID, int courseID) {
        try {
            if (!checkCartProduct(userID, courseID)) {
                return false;
            }
            connect();
            statement = conn.prepareStatement("delete from cart_product where userID = ? and courseID = ?");
            statement.setInt(1, userID);
            statement.setInt(2, courseID);
            statement.execute();
            disconnect();
            return !checkCartProduct(userID, courseID);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static ArrayList<Course> getAllCartProducts(int userID) {
        ArrayList<Course> courses = new ArrayList<>();

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select courseID from cart_product where userID = ?");
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int courseID = resultSet.getInt("courseID");
                Course course = getCourse(courseID);
                courses.add(course);
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return courses;
    }

    public static boolean addCourse(int organizationID, String name, String picture, double price) {
        try {
            boolean existOgranization = OrganizationDAO.existOrganization(organizationID);
            if (!existOgranization) {
                Logger.getLogger(LearnerDAO.class.getName()).log(Level.INFO, "ogranization not exist");
                return false;
            }
            if (name == null || name == "") {
                Logger.getLogger(LearnerDAO.class.getName()).log(Level.INFO, "invalid name");
                return false;
            }
            if (price < 0) {
                Logger.getLogger(LearnerDAO.class.getName()).log(Level.INFO, "invalid price");
                return false;
            }
        } catch (Exception e) {

        }
        Logger.getLogger(LearnerDAO.class.getName()).log(Level.INFO, "success");
        return true;
    }

    public static void main(String[] args) {

    }

}

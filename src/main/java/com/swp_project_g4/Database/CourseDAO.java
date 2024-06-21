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

    public static boolean existCourse(int courseId) {
        boolean ok = false;
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select courseId from course where courseId = ?");
            statement.setInt(1, courseId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getInt("courseId") == courseId) {
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

    public static Course getCourse(int courseId) {
        Course course = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from course where courseId = ?");
            statement.setInt(1, courseId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                course = new Course(
                        resultSet.getInt("courseId"),
                        resultSet.getInt("organizationId"),
                        resultSet.getString("name"),
                        resultSet.getString("picture"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("verified"),
                        resultSet.getInt("totalTime"),
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
                        resultSet.getInt("courseId"),
                        resultSet.getInt("organizationId"),
                        resultSet.getString("name"),
                        resultSet.getString("picture"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("verified"),
                        resultSet.getInt("totalTime"),
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
                        resultSet.getInt("courseId"),
                        resultSet.getInt("organizationId"),
                        resultSet.getString("name"),
                        resultSet.getString("picture"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("verified"),
                        resultSet.getInt("totalTime"),
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
                        resultSet.getInt("courseId"),
                        resultSet.getInt("organizationId"),
                        resultSet.getString("name"),
                        resultSet.getString("picture"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("verified"),
                        resultSet.getInt("totalTime"),
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

    public static int getSumTimeOfCourse(int courseId) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("""
                    select sum([time]) as sumTime from
                    (select * from lesson) as l
                    join
                    (select * from chapter where courseId = ?) as m
                    on l.chapterId = m.chapterId""");
            statement.setInt(1, courseId);
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

    public static int countCartProduct(int userId) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select count(*) as number from Cart where userId = ?");
            statement.setInt(1, userId);
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

    public static boolean checkCartProduct(int userId, int courseId) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select 1 from Cart where userId = ? and courseId = ?");
            statement.setInt(1, userId);
            statement.setInt(2, courseId);
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

    public static boolean insertCartProduct(int userId, int courseId) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("insert into Cart(userId, courseId) values (?,?)");
            statement.setInt(1, userId);
            statement.setInt(2, courseId);

            statement.execute();

            //disconnect to database
            disconnect();
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    public static boolean deleteCartProduct(int userId, int courseId) {
        try {
            if (!checkCartProduct(userId, courseId)) {
                return false;
            }
            connect();
            statement = conn.prepareStatement("delete from Cart where userId = ? and courseId = ?");
            statement.setInt(1, userId);
            statement.setInt(2, courseId);
            statement.execute();
            disconnect();
            return !checkCartProduct(userId, courseId);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static ArrayList<Course> getAllCartProducts(int userId) {
        ArrayList<Course> courses = new ArrayList<>();

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select courseId from Cart where userId = ?");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int courseId = resultSet.getInt("courseId");
                Course course = getCourse(courseId);
                courses.add(course);
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return courses;
    }

    public static boolean addCourse(int organizationId, String name, String picture, double price) {
        try {
            boolean existOgranization = OrganizationDAO.existOrganization(organizationId);
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

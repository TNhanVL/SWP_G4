/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Database;

import com.swp_project_g4.Model.Course;
import com.swp_project_g4.Model.Mooc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gr4
 */
public class CourseDAO extends DBConnection {

    public static boolean existCourse(int ID) {
        boolean ok = false;
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select ID from course where ID = ?");
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

    public static Course getCourse(int ID) {
        Course course = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from course where ID = ?");
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                course = new Course(
                        resultSet.getInt("ID"),
                        resultSet.getString("title"),
                        resultSet.getString("image"),
                        resultSet.getString("description"),
                        resultSet.getInt("organizationID"),
                        resultSet.getInt("lecturerID"),
                        resultSet.getDouble("unSalePrice"),
                        resultSet.getDouble("price"),
                        resultSet.getDouble("rate"));
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
                        resultSet.getInt("ID"),
                        resultSet.getString("title"),
                        resultSet.getString("image"),
                        resultSet.getString("description"),
                        resultSet.getInt("organizationID"),
                        resultSet.getInt("lecturerID"),
                        resultSet.getDouble("unSalePrice"),
                        resultSet.getDouble("price"),
                        resultSet.getDouble("rate"));
                courses.add(course);
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
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
                        resultSet.getInt("ID"),
                        resultSet.getString("title"),
                        resultSet.getString("image"),
                        resultSet.getString("description"),
                        resultSet.getInt("organizationID"),
                        resultSet.getInt("lecturerID"),
                        resultSet.getDouble("unSalePrice"),
                        resultSet.getDouble("price"),
                        resultSet.getDouble("rate"));
                courses.add(course);
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return courses;
    }

    public static boolean insertCourse(Course course) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("insert into course(title,[image],[description],organizationID,lecturerID,unSalePrice,price,rate) values (?,?,?,?,?,?,?,?)");
            statement.setString(1, course.getTitle());
            statement.setString(2, course.getImage());
            statement.setString(3, course.getDescription());
            statement.setInt(4, course.getOrganizationID());
            statement.setInt(5, course.getLecturerID());
            statement.setDouble(6, course.getUnSalePrice());
            statement.setDouble(7, course.getPrice());
            statement.setDouble(8, course.getRate());

            statement.execute();

            //disconnect to database
            disconnect();
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    public static boolean updateCourse(Course course) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("update course set title=?, [image]=?, [description]=?,organizationID=?,lecturerID=?,unSalePrice=?,price=?,rate=? where ID =?");
            statement.setString(1, course.getTitle());
            statement.setString(2, course.getImage());
            statement.setString(3, course.getDescription());
            statement.setInt(4, course.getOrganizationID());
            statement.setInt(5, course.getLecturerID());
            statement.setDouble(6, course.getUnSalePrice());
            statement.setDouble(7, course.getPrice());
            statement.setDouble(8, course.getRate());
            statement.setInt(9, course.getID());

            statement.executeUpdate();

            //disconnect to database
            disconnect();
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean deleteCourse(int ID) {
        try {
            if (!existCourse(ID)) {
                return false;
            }
            connect();
            statement = conn.prepareStatement("delete from course where ID=?");
            statement.setInt(1, ID);
            statement.execute();
            disconnect();
            if (!existCourse(ID)) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static int getSumTimeOfCourse(int courseID) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select sum([time]) as sumTime from\n"
                    + "(select * from lesson) as l\n"
                    + "join\n"
                    + "(select * from mooc where courseID = ?) as m\n"
                    + "on l.moocID = m.ID");
            statement.setInt(1, courseID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("sumTime");
            }

            //disconnect to database
            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return result
        return 0;
    }

    public static int getSumTimeCompletedOfCourse(int userID, int courseID) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select sum([time]) as sumTime from\n"
                    + "(select l.ID, [time] from\n"
                    + "(select * from lesson) as l\n"
                    + "join\n"
                    + "(select * from mooc where courseID = ?) as m\n"
                    + "on l.moocID = m.ID) l\n"
                    + "join\n"
                    + "(select * from lessonCompleted where userID = ?) lc\n"
                    + "on l.ID = lc.lessonID");
            statement.setInt(1, courseID);
            statement.setInt(2, userID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("sumTime");
            }

            //disconnect to database
            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return result
        return 0;
    }

    public static int getSumTimeCompletedOfAllCourses(int userID) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select sum([time]) as sumTime from\n"
                    + "(select l.ID, [time] from\n"
                    + "(select * from lesson) as l\n"
                    + "join\n"
                    + "(select * from mooc where courseID in (select courseID from purchasedCourse where userID = ?)) as m\n"
                    + "on l.moocID = m.ID) l\n"
                    + "join\n"
                    + "(select * from lessonCompleted where userID = ?) lc\n"
                    + "on l.ID = lc.lessonID");
            statement.setInt(1, userID);
            statement.setInt(2, userID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("sumTime");
            }

            //disconnect to database
            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return result
        return 0;
    }

    public static int countOrderCourse(int userID) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select count(*) as number from orderCourse where userID = ?");
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("number");
            }

            //disconnect to database
            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return result
        return 0;
    }

    public static boolean checkOrderCourse(int userID, int courseID) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select 1 from orderCourse where userID = ? and courseID = ?");
            statement.setInt(1, userID);
            statement.setInt(2, courseID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }

            //disconnect to database
            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return result
        return false;
    }

    public static boolean insertOrderCourse(int userID, int courseID) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("insert into orderCourse(userID, courseID) values (?,?)");
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

    public static boolean deleteOrderCourse(int userID, int courseID) {
        try {
            if (!checkOrderCourse(userID, courseID)) {
                return false;
            }
            connect();
            statement = conn.prepareStatement("delete from orderCourse where userID = ? and courseID = ?");
            statement.setInt(1, userID);
            statement.setInt(2, courseID);
            statement.execute();
            disconnect();
            return !checkOrderCourse(userID, courseID);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static ArrayList<Course> getAllOrderCourses(int userID) {
        ArrayList<Course> courses = new ArrayList<>();

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select courseID from orderCourse where userID = ?");
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

    public static boolean checkPurchasedCourse(int userID, int courseID) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select 1 from purchasedCourse where userID = ? and courseID = ?");
            statement.setInt(1, userID);
            statement.setInt(2, courseID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }

            //disconnect to database
            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return result
        return false;
    }

    public static boolean insertPurchasedCourse(int userID, int courseID) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("insert into purchasedCourse(userID, courseID) values (?,?)");
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

    public static boolean deletePurchasedCourse(int userID, int courseID) {
        try {
            if (!checkPurchasedCourse(userID, courseID)) {
                return false;
            }
            connect();
            statement = conn.prepareStatement("delete from purchasedCourse where userID = ? and courseID = ?");
            statement.setInt(1, userID);
            statement.setInt(2, courseID);
            statement.execute();
            disconnect();
            return !checkPurchasedCourse(userID, courseID);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static ArrayList<Course> getAllPurchasedCourses(int userID) {
        ArrayList<Course> courses = new ArrayList<>();

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select courseID from purchasedCourse where userID = ?");
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

    public static ArrayList<Course> getAllCreatedCourses(int lectureID) {
        ArrayList<Course> courses = new ArrayList<>();

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from course where lecturerID = ?");
            statement.setInt(1, lectureID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int courseID = resultSet.getInt("ID");
                Course course = getCourse(courseID);
                courses.add(course);
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return courses;
    }

    public static int getNumberPurchasedCourse(int userID) {

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select count(*) as number from purchasedCourse where userID = ?");
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("number");
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }
    
    public static int getNumberPurchasedOfCourse(int courseID) {

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select count(*) as number from purchasedCourse where courseID = ?");
            statement.setInt(1, courseID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("number");
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public static int getNumberCreatedCourse(int userID) {

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select count(*) as number from course where lecturerID = ?");
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("number");
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public static int getNumberCompletedCourse(int userID) {

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select count(*) as number from [certificate] where userID = ?");
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("number");
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public static boolean checkCertificate(int userID, int courseID) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select 1 from [certificate] where userID = ? and courseID = ?");
            statement.setInt(1, userID);
            statement.setInt(2, courseID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }

            //disconnect to database
            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return result
        return false;
    }

    public static String getCertificateName(int userID, int courseID) {
        String certificateName = null;
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from [certificate] where userID = ? and courseID = ?");
            statement.setInt(1, userID);
            statement.setInt(2, courseID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                certificateName = resultSet.getString("certificateName");
            }

            //disconnect to database
            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return result
        return certificateName;
    }

    public static boolean insertCertificate(int userID, int courseID, String certificateName) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("insert into [certificate](userID, courseID, certificateName) values (?, ?, ?)");
            statement.setInt(1, userID);
            statement.setInt(2, courseID);
            statement.setString(3, certificateName);

            statement.execute();

            //disconnect to database
            disconnect();
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    public static boolean checkCourseCompleted(int userID, int courseID) {
        ArrayList<Mooc> moocs = MoocDAO.getMoocsByCourseID(courseID);
        for (Mooc mooc : moocs) {
            int numberOfCompleted = LessonDAO.getNumberLessonsCompleted(userID, mooc.getID());
            int numberOfLesson = LessonDAO.getNumberLessonsByMoocID(mooc.getID());
            if (numberOfCompleted != numberOfLesson) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
//        System.out.println(existCourse(1));
//
//        Course c = new Course(11, "Java basic", "https://d3njjcbhbojbot.cloudfront.net/api/utilities/v1/imageproxy/https://d15cw65ipctsrr.cloudfront.net/17/6b66f0a7ea11e7a885e33e8374f520/software_development_lifecycle_logo_pexels_CC0.jpg?auto=format&dpr=1&w=100&h=100&fit=clamp", "ezsy", 1, 1, 1000, 500, 4.5);
//        insertCourse(c);

//        System.out.println(checkOrderCourse(1, 1));
//
//        System.out.println(getCourse(11));
        System.out.println(getSumTimeCompletedOfAllCourses(1));
//
//        c.setDescription("Normal");
//
//        updateCourse(c);
//
//        System.out.println(getCourse(11));

//        deleteCourse(13);
//        deleteCourse(12);
//        deleteCourse(11);
//        System.out.println(existCourse(1));
    }

}

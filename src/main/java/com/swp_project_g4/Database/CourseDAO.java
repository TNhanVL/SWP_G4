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
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
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
                        resultSet.getInt("instructorID"),
                        resultSet.getString("name"),
                        resultSet.getString("picture"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("verify"),
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
                        resultSet.getInt("instructorID"),
                        resultSet.getString("name"),
                        resultSet.getString("picture"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("verify"),
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
                        resultSet.getInt("instructorID"),
                        resultSet.getString("name"),
                        resultSet.getString("picture"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("verify"),
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
                        resultSet.getInt("instructorID"),
                        resultSet.getString("name"),
                        resultSet.getString("picture"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("verify"),
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

    public static boolean insertCourse(Course course) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("insert into course(name,[picture],[description],organizationID,instructorID,price,rate) values (?,?,?,?,?,?,?)");
            statement.setString(1, course.getName());
            statement.setString(2, course.getPicture());
            statement.setString(3, course.getDescription());
            statement.setInt(4, course.getOrganizationID());
            statement.setInt(5, course.getInstructorID());
            statement.setDouble(6, course.getPrice());
            statement.setDouble(7, course.getRate());

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

            statement = conn.prepareStatement("update course set name=?, [picture]=?, [description]=?,organizationID=?,instructorID=?,price=?,rate=? where courseID =?");
            statement.setString(1, course.getName());
            statement.setString(2, course.getPicture());
            statement.setString(3, course.getDescription());
            statement.setInt(4, course.getOrganizationID());
            statement.setInt(5, course.getInstructorID());
            statement.setDouble(6, course.getPrice());
            statement.setDouble(7, course.getRate());
            statement.setInt(8, course.getCourseID());

            statement.executeUpdate();

            //disconnect to database
            disconnect();
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean deleteCourse(int courseID) {
        try {
            if (!existCourse(courseID)) {
                return false;
            }
            connect();
            statement = conn.prepareStatement("delete from course where courseID=?");
            statement.setInt(1, courseID);
            statement.execute();
            disconnect();
            return !existCourse(courseID);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
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
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return result
        return 0;
    }

    public static int getSumTimeCompletedOfCourse(int userID, int courseID) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("""
                    select sum([time]) as sumTime from
                    (select l.lessonID, [time] from
                    (select * from lesson) as l
                    join
                    (select * from chapter where courseID = ?) as m
                    on l.chapterID = m.chapterID) l
                    join
                    (select * from lesson_completed where userID = ?) lc
                    on l.lessonID = lc.lessonID""");
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

            statement = conn.prepareStatement("""
                    select sum([time]) as sumTime from
                    (select l.lessonID, [time] from
                    (select * from lesson) as l
                    join
                    (select * from chapter where courseID in (select courseID from purchased_course where userID = ?)) as m
                    on l.chapterID = m.chapterID) l
                    join
                    (select * from lesson_completed where userID = ?) lc
                    on l.lessonID = lc.lessonID""");
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
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
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

    public static boolean checkPurchasedCourse(int userID, int courseID) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select 1 from purchased_course where userID = ? and courseID = ?");
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

            statement = conn.prepareStatement("insert into purchased_course(userID, courseID) values (?,?)");
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
            statement = conn.prepareStatement("delete from purchased_course where userID = ? and courseID = ?");
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

            statement = conn.prepareStatement("select courseID from purchased_course where userID = ?");
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

            statement = conn.prepareStatement("select * from course where instructorID = ?");
            statement.setInt(1, lectureID);
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

    public static int getNumberPurchasedCourse(int userID) {

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select count(*) as number from purchased_course where userID = ?");
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

            statement = conn.prepareStatement("select count(*) as number from purchased_course where courseID = ?");
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

            statement = conn.prepareStatement("select count(*) as number from course where instructorID = ?");
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
                certificateName = resultSet.getString("certificate_name");
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

            statement = conn.prepareStatement("insert into [certificate](userID, courseID, certificate_name) values (?, ?, ?)");
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

    public static boolean markCourseCompleted(int userID, int courseID) {
        ArrayList<Chapter> chapters = ChapterDAO.getChaptersByCourseID(courseID);
        for (Chapter chapter : chapters) {
            int numberOfCompleted = LessonDAO.getNumberLessonsCompleted(userID, chapter.getChapterID());
            int numberOfLesson = LessonDAO.getNumberLessonsByChapterID(chapter.getChapterID());
            if (numberOfCompleted != numberOfLesson) {
                return false;
            }
        }

        return true;
    }

    public static boolean addCourse(int organizationID, String name, String picture, double price) {
        try {
            boolean existOgranization = OrganizationDAO.existOrganization(organizationID);
            if (!existOgranization) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.INFO, "ogranization not exist");
                return false;
            }
            if (name == null || name == "") {
                Logger.getLogger(UserDAO.class.getName()).log(Level.INFO, "invalid name");
                return false;
            }
            if (price < 0) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.INFO, "invalid price");
                return false;
            }
        } catch (Exception e) {

        }
        Logger.getLogger(UserDAO.class.getName()).log(Level.INFO, "success");
        return true;
    }

    public static void main(String[] args) {
//        System.out.println(existCourse(1));
//
//        Course c = new Course(11, "Java basic", "https://d3njjcbhbojbot.cloudfront.net/api/utilities/v1/imageproxy/https://d15cw65ipctsrr.cloudfront.net/17/6b66f0a7ea11e7a885e33e8374f520/software_development_lifecycle_logo_pexels_CC0.jpg?auto=format&dpr=1&w=100&h=100&fit=clamp", "ezsy", 1, 1, 1000, 500, 4.5);
//        insertCourse(c);

//        System.out.println(checkCartProduct(1, 1));
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

package com.swp_project_g4.Database;

import com.swp_project_g4.Model.Instructor;
import com.swp_project_g4.Model.Review;
import com.swp_project_g4.Model.User;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReviewDAO extends DBConnection {


//    public static boolean insertReview(Review review) {
//        boolean result = false;
//        try {
//            //connect to database
//            connect();
//
//            statement = conn.prepareStatement("insert into review(userID, courseID, reviewed, verified, note) values (?,?,?,?,?)");
////            statement.setInt(1,Review.getReviewID());
//            statement.setInt(1, review.getUserID());
//            statement.setInt(2, review.getCourseID());
//            statement.setBoolean(3, review.isReviewed());
//            statement.setBoolean(4, review.isVerified());
//            statement.setString(5, review.getNote());
//            statement.execute();
//            //Indentify the last ID inserted
//            //disconnect to database
//            disconnect();
//
//            result = true;
//        } catch (SQLException | ClassNotFoundException ex) {
//            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return result;
//    }


    public static boolean updateReview(Review review) {

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("update review set courseID = ?,reviewed=?,verified=?,note=? where userID = ?");
            statement.setInt(1, review.getCourseID());
            statement.setBoolean(2, review.isReviewed());
            statement.setBoolean(3,review.isVerified());
            statement.setString(4,review.getNote());
            statement.setInt(5,review.getID());
            statement.executeUpdate();

            //disconnect to database
            disconnect();
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }
    public static boolean deleteInstructor(int userID) {
        try {

            connect();
            statement = conn.prepareStatement("delete from review where userID=?");
            statement.setInt(1, userID);
            statement.execute();
            disconnect();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }


    public static void main(String[] args) {

//

    }
}

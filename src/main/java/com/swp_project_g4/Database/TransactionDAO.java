package com.swp_project_g4.Database;

import com.swp_project_g4.Model.Instructor;
import com.swp_project_g4.Model.Review;
import com.swp_project_g4.Model.Transaction;
import com.swp_project_g4.Model.User;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransactionDAO extends DBConnection {
    public static boolean insertTransaction(Transaction transaction) {
        boolean result = false;
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("insert into [transaction](userID, courseID, originPrice, price, type, description, status)\n" +
                    "values(?,?,?,?,?,?,?)");
//            statement.setInt(1,Review.getReviewID());
            statement.setInt(1, transaction.getID());
            statement.setInt(2, transaction.getCourse());
            statement.setFloat(3, transaction.getOrginPrice());
            statement.setFloat(4, transaction.getPrice());
            statement.setInt(5, transaction.getType());
            statement.setString(6,transaction.getDescription());
            statement.setInt(7,transaction.getStatus());
            statement.execute();
            //Indentify the last ID inserted
            //disconnect to database
            disconnect();

            result = true;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;

    }

    public static boolean updateTransaction(Transaction transaction) {

        try {
            //connect to database
            connect();
            statement = conn.prepareStatement("update [transaction] set courseID = ? , originPrice = ?,price = ?,type =?,description =?,status =?where userID =?\n");
            statement.setInt(1, transaction.getCourse());
            statement.setFloat(2, transaction.getOrginPrice());
            statement.setFloat(3,transaction.getPrice());
            statement.setString(5,transaction.getDescription());
            statement.setInt(6,transaction.getType());
            statement.setInt(7,transaction.getStatus());
            statement.setInt(8,transaction.getID());
            statement.executeUpdate();
            //disconnect to database
            disconnect();
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    public static boolean deleteTransaction(int userID) {
        try {

            connect();
            statement = conn.prepareStatement("delete from [transaction] where userID =?");
            statement.setInt(1, userID);
            statement.execute();
            disconnect();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

public static void main(String[] args) {

}
}
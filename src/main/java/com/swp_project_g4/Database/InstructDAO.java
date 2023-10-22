package com.swp_project_g4.Database;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InstructDAO extends DBConnection {

    public static boolean insertIntruct(int userID, int courseID) {
        try {
            //connect to database
            connect();
            statement = conn.prepareStatement("insert into instruct(userID, courseID)values (?,?)");
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
    public static void deleteIntruct(int userID, int courseID) {
        try {
            connect();
            statement = conn.prepareStatement("delete from instruct where userID = ? and courseID = ?");
            statement.setInt(1, userID);
            statement.setInt(2, courseID);
            statement.execute();
            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

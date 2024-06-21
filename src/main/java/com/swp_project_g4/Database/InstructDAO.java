package com.swp_project_g4.Database;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InstructDAO extends DBConnection {

    public static boolean insertIntruct(int userId, int courseId) {
        try {
            //connect to database
            connect();
            statement = conn.prepareStatement("insert into instruct(userId, courseId)values (?,?)");
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
    public static void deleteIntruct(int userId, int courseId) {
        try {
            connect();
            statement = conn.prepareStatement("delete from instruct where userId = ? and courseId = ?");
            statement.setInt(1, userId);
            statement.setInt(2, courseId);
            statement.execute();
            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

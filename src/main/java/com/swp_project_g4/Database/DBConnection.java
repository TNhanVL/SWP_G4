/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Database;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gr2
 */
public class DBConnection {

    static Connection conn;
    static PreparedStatement statement;

    static void connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDataSource");
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName(Config.SERVER);
        ds.setUser(Config.USER);
        ds.setPassword(Config.PASSWORD);
        ds.setPortNumber(Config.PORT);
        ds.setDatabaseName(Config.DATABASE_NAME);
        ds.setEncrypt(false);

        conn = ds.getConnection();
    }

    static void disconnect() throws SQLException {
        conn.close();
    }

    public static int lastModifyID(Connection conn) {
        int lastModifyID = 0;
        try {
            statement = conn.prepareStatement("SELECT @@IDENTITY AS newID");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                lastModifyID = resultSet.getInt("newID");
            } else {
                return -1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);

        }

        return lastModifyID;
    }

    public static void main(String[] args) throws ClassNotFoundException {

        try {
            connect();

            statement = conn.prepareStatement("UPDATE [user] SET firstName = ?  WHERE ID = 4");
            statement.setString(1, "Thanh Nhân");
            statement.execute();

            disconnect();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
}

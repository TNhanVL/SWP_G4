/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Database;

import com.swp_project_g4.Model.Learner;
import com.swp_project_g4.Service.MD5;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TTNhan
 */
public class LearnerDAO extends DBConnection {

    public static boolean existUser(String username) {
        boolean ok = false;
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select username from [learner] where username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getString("username").equals(username)) {
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

    /**
     * check password of a user
     *
     * @param username
     * @param password
     * @param hashed true if password hashed
     * @return 0 - ok; 1 - not exist; 2 - incorrect pw
     */
    public static int checkUser(String username, String password, boolean hashed) {
        int status = -1;
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select [password] from [learner] where username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            //not exist
            if (resultSet == null || !resultSet.next()) {
                status = 1;
            } else {
                String pw = resultSet.getString("password");
                if (!hashed) {
                    password = MD5.getMd5(password);
                }
                if (pw.equals(password)) {
                    //correct
                    status = 0;
                } else {
                    //not correct password
                    status = 2;
                }
            }

            //disconnect to database
            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LearnerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return result
        return status;
    }

    public static boolean checkUserPassword(String username, String password, boolean hashed) {
        boolean res = false;
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select [password] from [learner] where username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            //not exist
            if (resultSet == null || !resultSet.next()) {
                Logger.getLogger(LearnerDAO.class.getName()).log(Level.INFO, "not exist user");
            } else {
                String pw = resultSet.getString("password");
                if (!hashed) {
                    password = MD5.getMd5(password);
                }
                if (pw.equals(password)) {
                    //correct
                    res = true;
                    Logger.getLogger(LearnerDAO.class.getName()).log(Level.INFO, "success");
                }else{
                    Logger.getLogger(LearnerDAO.class.getName()).log(Level.INFO, "wrong password");
                }
            }

            //disconnect to database
            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LearnerDAO.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(LearnerDAO.class.getName()).log(Level.INFO, "execute query failed");
        }
        return res;
    }

    public static Learner getUser(int ID) {
        Learner learner = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from [learner] where learnerID = ?");
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                learner = new Learner(
                        resultSet.getInt("learnerID"),
                        resultSet.getString("picture"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getBoolean("email_verified"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getDate("birthday"),
                        resultSet.getInt("countryID"),
                        resultSet.getInt("status")
                );
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return learner;
    }

    public static Learner getUserByEmail(String email) {
        Learner learner = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from [learner] where email = ?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                learner = new Learner(
                        resultSet.getInt("learnerID"),
                        resultSet.getString("picture"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getBoolean("email_verified"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getDate("birthday"),
                        resultSet.getInt("countryID"),
                        resultSet.getInt("status")
                );
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return learner;
    }

    public static Learner getUserByUsername(String username) {
        Learner learner = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from [learner] where username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                learner = new Learner(
                        resultSet.getInt("learnerID"),
                        resultSet.getString("picture"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getBoolean("email_verified"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getDate("birthday"),
                        resultSet.getInt("countryID"),
                        resultSet.getInt("status")
                );
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return learner;
    }

    public static ArrayList<Learner> getAllUsers() {
        ArrayList<Learner> list = new ArrayList<>();

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from [learner]");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Learner learner = new Learner(
                        resultSet.getInt("learnerID"),
                        resultSet.getString("picture"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getBoolean("email_verified"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getDate("birthday"),
                        resultSet.getInt("countryID"),
                        resultSet.getInt("status")
                );
                list.add(learner);
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public static int insertUser(Learner learner) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            connect();

            statement = conn.prepareStatement("insert into [learner](picture,username,[password],email,first_name,last_name,birthday,countryID,status) values(?,?,?,?,?,?,?,?,?)");
            statement.setString(1, learner.getPicture());
            statement.setString(2, learner.getUsername());
            statement.setString(3, learner.getPassword());
            statement.setString(4, learner.getEmail());
            statement.setString(5, learner.getFirstName());
            statement.setString(6, learner.getLastName());
            statement.setString(7, dateFormat.format(learner.getBirthday()));
            statement.setInt(8, learner.getCountryID());
            statement.setInt(9, learner.getStatus());
            statement.executeUpdate();

            int newID = lastModifyID(conn);

            disconnect();
            return newID;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);

        }
        return -1;
    }

    public static boolean updateUser(Learner learner) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            connect();

            statement = conn.prepareStatement("UPDATE [learner] SET picture = ?, username = ?, [password] = ?, email = ?, first_name = ?, last_name = ?, birthday = ?, countryID = ?, [status] = ? WHERE learnerID = ?");
            statement.setString(1, learner.getPicture());
            statement.setString(2, learner.getUsername());
            statement.setString(3, learner.getPassword());
            statement.setString(4, learner.getEmail());
            statement.setString(5, learner.getFirstName());
            statement.setString(6, learner.getLastName());
            statement.setString(7, dateFormat.format(learner.getBirthday()));
            statement.setInt(8, learner.getCountryID());
            statement.setInt(9, learner.getStatus());
            statement.setInt(10, learner.getID());
            statement.execute();

            disconnect();

            return true;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    public static boolean deleteUser(int ID) {
        try {
            Learner learner = getUser(ID);

            if (learner == null) {
                return false;
            }
            connect();
            statement = conn.prepareStatement("delete from [learner] where learnerID=?");
            statement.setInt(1, ID);
            statement.execute();
            disconnect();
            return true;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void main(String[] args) throws ClassNotFoundException {

    }
}

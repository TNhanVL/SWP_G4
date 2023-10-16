/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Database;

import com.swp_project_g4.Model.User;
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
public class UserDAO extends DBConnection {

    public static boolean existUser(String username) {
        boolean ok = false;
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select username from [user] where username = ?");
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
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
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

            statement = conn.prepareStatement("select [password] from [user] where username = ?");
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
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return result
        return status;
    }

    public static User getUser(int ID) {
        User user = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from [user] where ID = ?");
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User(
                        resultSet.getInt("ID"),
                        resultSet.getString("avatar"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getInt("role"),
                        resultSet.getDate("birthday"),
                        resultSet.getInt("countryID"),
                        resultSet.getInt("status")
                );
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

    public static User getUserByEmail(String email) {
        User user = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from [user] where email = ?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User(
                        resultSet.getInt("ID"),
                        resultSet.getString("avatar"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getInt("role"),
                        resultSet.getDate("birthday"),
                        resultSet.getInt("countryID"),
                        resultSet.getInt("status")
                );
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

    public static User getUserByUsername(String username) {
        User user = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from [user] where username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User(
                        resultSet.getInt("ID"),
                        resultSet.getString("avatar"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getInt("role"),
                        resultSet.getDate("birthday"),
                        resultSet.getInt("countryID"),
                        resultSet.getInt("status")
                );
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

    public static ArrayList<User> getAllUsers() {
        ArrayList<User> list = new ArrayList<>();

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from [user]");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("ID"),
                        resultSet.getString("avatar"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getInt("role"),
                        resultSet.getDate("birthday"),
                        resultSet.getInt("countryID"),
                        resultSet.getInt("status")
                );
                list.add(user);
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public static int insertUser(User user) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            connect();

            statement = conn.prepareStatement("insert into [user](avatar,username,[password],email,firstName,lastName,[role],birthday,countryID,status) values(?,?,?,?,?,?,?,?,?,?)");
            statement.setString(1, user.getAvatar());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getFirstName());
            statement.setString(6, user.getLastName());
            statement.setInt(7, user.getRole());
            statement.setString(8, dateFormat.format(user.getBirthday()));
            statement.setInt(9, user.getCountryID());
            statement.setInt(10, user.getStatus());
            statement.executeUpdate();

            int newID = lastModifyID(conn);

            disconnect();
            return newID;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);

        }
        return -1;
    }

    public static boolean updateUser(User user) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            connect();

            statement = conn.prepareStatement("UPDATE [user] SET avatar = ?, username = ?, [password] = ?, email = ?, firstName = ?, lastName = ?, role = ?, birthday = ?, countryID = ?, [status] = ? WHERE ID = ?");
            statement.setString(1, user.getAvatar());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getFirstName());
            statement.setString(6, user.getLastName());
            statement.setInt(7, user.getRole());
            statement.setString(8, dateFormat.format(user.getBirthday()));
            statement.setInt(9, user.getCountryID());
            statement.setInt(10, user.getStatus());
            statement.setInt(11, user.getID());
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
            User user = getUser(ID);

            if (user == null) {
                return false;
            }
            connect();
            statement = conn.prepareStatement("delete from [user] where ID=?");
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
//<<<<<<< HEAD
////<<<<<<< HEAD
//=======
//>>>>>>> 6cccdf640766a2ed2b76a67412cee356e5dd9750
//        User user = null;
//
//        try {
//            //connect to database
//            connect();
//
//            statement = conn.prepareStatement("select * from [user] where ID = ?");
//            statement.setString(1, "3");
//            ResultSet resultSet = statement.executeQuery();
//
//            if (resultSet.next()) {
//                user = new User(
//                        resultSet.getInt("ID"),
//                        resultSet.getString("avatar"),
//                        resultSet.getString("username"),
//                        resultSet.getString("password"),
//                        resultSet.getString("email"),
//                        resultSet.getString("firstName"),
//                        resultSet.getString("lastName"),
//                        resultSet.getDate("birthday"),
//                        resultSet.getInt("countryID"),
//                        resultSet.getInt("status")
//                );
//            }
//
//            disconnect();
//        } catch (SQLException | ClassNotFoundException ex) {
//            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
////        System.out.println(user);
//        user.setAvatar("https://ex.png");
//        user.setUsername("duongthanh");
//
////        System.out.println(insertUser(user));
////        updateUser(user);
////          deleteUser(5);
//        ArrayList<User> users = getUser();
//
//        for (User u : users) {
//            System.out.println(u);
//        }
//<<<<<<< HEAD
//
////    User s = getUser(1);
////        System.out.println(s.toString());
////=======
////        System.out.println(getUserByEmail("user1@example.com"));
////>>>>>>> b1683b36740c8f06ec9e65a7e66ebac662c6a69b
//=======
//        System.out.println(getUserByEmail("user1@example.com"));
//>>>>>>> 6cccdf640766a2ed2b76a67412cee356e5dd9750
    }
}

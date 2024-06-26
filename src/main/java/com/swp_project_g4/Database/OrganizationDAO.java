/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Database;

import com.swp_project_g4.Model.Organization;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author TTNhan
 */
public class OrganizationDAO extends DBConnection {

    public static boolean existOrganization(int ID) {
        boolean ok = false;
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select organizationId from organization where organizationId = ?");
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getInt("organizationId") == ID) {
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

    public static Organization getOrganization(int ID) {
        Organization organization = null;

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from organization where organizationId = ?");
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                organization = new Organization(
                        resultSet.getInt("organizationId"),
                        resultSet.getInt("countryId"),

                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("picture"),
                        resultSet.getString("name"),
                        resultSet.getString("description"));
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return organization;
    }

    public static ArrayList<Organization> getAllOrganization() {
        ArrayList<Organization> organizationList = new ArrayList<>();
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("select * from organization order by organizationId");
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                organizationList.add(new Organization(
                        resultSet.getInt("organizationId"),
                        resultSet.getInt("countryId"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("picture"),
                        resultSet.getString("name"),
                        resultSet.getString("description"))
                );
            }

            disconnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return organizationList;
    }

    public static boolean insertOrganization(Organization organization) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("insert into organization(name,picture,description) values (?,?,?)");
            statement.setString(1, organization.getName());
            statement.setString(2, organization.getPicture());
            statement.setString(3, organization.getDescription());
            statement.executeUpdate();

            //disconnect to database
            disconnect();
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    public static boolean updateOrganization(Organization organization) {
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("update organization set name=?,picture=?,description=?,countryId=?,username=?,password=?,email=? where organizationId =?");

            statement.setString(1, organization.getName());
            statement.setString(2, organization.getPicture());
            statement.setString(3, organization.getDescription());
            statement.setInt(4, organization.getCountryId());
            statement.setString(5, organization.getUsername());
            statement.setString(6, organization.getPassword());
            statement.setString(7, organization.getEmail());
            statement.setInt(8, organization.getID());

            statement.executeUpdate();

            //disconnect to database
            disconnect();
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean deleteOrganization(int ID) {
        try {
            if (!existOrganization(ID)) {
                return false;
            }
            connect();
            statement = conn.prepareStatement("delete from organization where organizationId=?");
            statement.setInt(1, ID);
            statement.execute();
            disconnect();
            return !existOrganization(ID);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(existOrganization(1));
//        Organization o = new Organization(1, "Vin Group", "htttps://VinID.jpg", "very well");
////        System.out.println(getOrganization(2));
//        o.setLogo("htttps://VinID.logo.jpg");
//        o.setID(2);
//        o.setDescription("well");
//        o.setName("FPT");
//        updateOrganization(o);
//        deleteOrganization(4);
    }
}

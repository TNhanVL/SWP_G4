/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Database;

import com.swp_project_g4.Model.Organization;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thanh Duong
 */
public class OrganizationDAO extends DBConnection {

    

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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Model;

/**
 *
 * @author Thanh Duong
 */
public class Lecturer extends User {

    private int organizationID;

    public Lecturer() {
    }

    public Lecturer(User user, int organizationID) {
        super(user);
        this.organizationID = organizationID;
    }

    public int getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(int organizationID) {
        this.organizationID = organizationID;
    }
}

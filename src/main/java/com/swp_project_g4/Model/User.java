package com.swp_project_g4.Model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

/**
 * @author TTNhan
 */
@Entity
@Table(name = "[user]")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String picture;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private int role;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private int countryID;
    private int status;

    public User() {
        this.role = 1;
        this.status = 1;
    }

    public User(int ID, String picture, String username, String password, String email, String firstName, String lastName, int role, Date birthday, int countryID, int status) {
        this.ID = ID;
        this.picture = picture;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.birthday = birthday;
        this.countryID = countryID;
        this.status = status;
    }

    public User(User user) {
        this.ID = user.ID;
        this.picture = user.picture;
        this.username = user.username;
        this.password = user.password;
        this.email = user.email;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.role = user.role;
        this.birthday = user.birthday;
        this.countryID = user.countryID;
        this.status = user.status;
    }

    public User(GooglePojo googlePojo) {
        this.email = googlePojo.getEmail();
        this.firstName = googlePojo.getGiven_name();
        this.lastName = googlePojo.getFamily_name();
        this.picture = googlePojo.getPicture();
        this.role = 1;
        this.status = 1;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" + "ID=" + ID + ", picture=" + picture + ", username=" + username + ", password=" + password + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", birthday=" + birthday + ", countryID=" + countryID + ", status=" + status + '}';
    }

}

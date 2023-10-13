package com.swp_project_g4.Model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Thanh Duong
 */
public class User {

    private int ID;
    private String avatar;
    private String username;
    private String password;
    private String email;
    private String firstname;
    private String lastname;
    private int role;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private int countryID;
    private int status;

    public User() {
        this.role = 1;
        this.status = 1;
    }

    public User(int ID, String avatar, String username, String password, String email, String firstname, String lastname, int role, Date birthday, int countryID, int status) {
        this.ID = ID;
        this.avatar = avatar;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
        this.birthday = birthday;
        this.countryID = countryID;
        this.status = status;
    }

    public User(User user) {
        this.ID = user.ID;
        this.avatar = user.avatar;
        this.username = user.username;
        this.password = user.password;
        this.email = user.email;
        this.firstname = user.firstname;
        this.lastname = user.lastname;
        this.role = user.role;
        this.birthday = user.birthday;
        this.countryID = user.countryID;
        this.status = user.status;
    }

    public User(GooglePojo googlePojo) {
        this.email = googlePojo.getEmail();
        this.firstname = googlePojo.getGiven_name();
        this.lastname = googlePojo.getFamily_name();
        this.avatar = googlePojo.getPicture();
        this.role = 1;
        this.status = 1;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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
        return firstname;
    }

    public void setFirstName(String firstName) {
        this.firstname = firstName;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastName) {
        this.lastname = lastName;
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
        return "User{" + "ID=" + ID + ", avatar=" + avatar + ", username=" + username + ", password=" + password + ", email=" + email + ", firstname=" + firstname + ", lastname=" + lastname + ", birthday=" + birthday + ", countryID=" + countryID + ", status=" + status + '}';
    }

}

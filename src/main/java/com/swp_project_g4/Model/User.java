package com.swp_project_g4.Model;

import java.util.Date;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

/**
 * @author TTNhan
 */
@Entity
@Table(name = "[user]")
@Data
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

}

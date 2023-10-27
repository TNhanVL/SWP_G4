package com.swp_project_g4.Model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

/**
 * @author TTNhan
 */
@Entity
@Table(name = "[learner]")
@Data
@AllArgsConstructor
public class User extends User1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String firstName;
    private String lastName;
    private int role;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    public User() {
        this.role = 1;
        this.setStatus(1);
    }

    public User(int ID, String picture, String username, String password, String email, String firstName, String lastName, int role, Date birthday, int countryID, int status) {
        super(picture, username, password, email, countryID, status);
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.birthday = birthday;
    }

    public User(User user) {
        super(user);
        this.ID = user.ID;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.role = user.role;
        this.birthday = user.birthday;
    }

    public User(GooglePojo googlePojo) {
        super(googlePojo);
        this.firstName = googlePojo.getGiven_name();
        this.lastName = googlePojo.getFamily_name();
        this.role = 1;
    }

}

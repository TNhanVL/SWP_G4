package com.swp_project_g4.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author TTNhan
 */
@MappedSuperclass
@Data
@AllArgsConstructor
public class User1 {
    private String picture;
    private String username;
    private String password;
    private String email;
    private int countryID;
    private int status;

    public User1() {
        this.status = 1;
    }

    public User1(User1 user) {
        this.picture = user.picture;
        this.username = user.username;
        this.password = user.password;
        this.email = user.email;
        this.countryID = user.countryID;
        this.status = user.status;
    }

    public User1(GooglePojo googlePojo) {
        this.email = googlePojo.getEmail();
        this.picture = googlePojo.getPicture();
        this.status = 1;
    }

}

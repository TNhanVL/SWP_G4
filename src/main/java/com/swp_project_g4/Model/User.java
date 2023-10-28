package com.swp_project_g4.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author TTNhan
 */
@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String picture;
    private String username;
    private String password;
    private String email;
    private int countryID;
    private int status;

    public User(User user) {
        this.picture = user.picture;
        this.username = user.username;
        this.password = user.password;
        this.email = user.email;
        this.countryID = user.countryID;
        this.status = user.status;
    }

    public User(GooglePojo googlePojo) {
        this.email = googlePojo.getEmail();
        this.picture = googlePojo.getPicture();
    }

}

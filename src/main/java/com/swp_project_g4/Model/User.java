package com.swp_project_g4.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    public User(String picture, String username, String password, String email, int countryID, int status) {
        this.picture = picture;
        this.username = username;
        this.password = password;
        this.email = email;
        this.countryID = countryID;
        this.status = status;
    }

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

    @ManyToOne
    @JoinColumn(name = "countryID", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Country country;

}

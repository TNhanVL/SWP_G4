package com.swp_project_g4.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

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
    private int countryId;
    private int status;

    public User(String picture, String username, String password, String email, int countryId, int status) {
        this.picture = picture;
        this.username = username;
        this.password = password;
        this.email = email;
        this.countryId = countryId;
        this.status = status;
    }

    public User(User user) {
        this.picture = user.picture;
        this.username = user.username;
        this.password = user.password;
        this.email = user.email;
        this.countryId = user.countryId;
        this.status = user.status;
    }

    public User(GooglePojo googlePojo) {
        this.email = googlePojo.getEmail();
        this.picture = googlePojo.getPicture();
    }

    @ManyToOne
    @JoinColumn(name = "countryId", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Country country;

}

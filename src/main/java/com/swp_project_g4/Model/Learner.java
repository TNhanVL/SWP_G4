package com.swp_project_g4.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

/**
 * @author TTNhan
 */
@Entity
@Table(name = "[learner]")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Learner extends User {
    @Column(name = "learnerID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String firstName;
    private String lastName;
    private boolean emailVerified;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    public Learner(int ID, String picture, String username, String password, String email, boolean emailVerified, String firstName, String lastName, Date birthday, int countryID, int status) {
        super(picture, username, password, email, countryID, status);
        this.ID = ID;
        this.emailVerified = emailVerified;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }

    public Learner(Learner learner) {
        super(learner);
        this.ID = learner.ID;
        this.firstName = learner.firstName;
        this.lastName = learner.lastName;
        this.birthday = learner.birthday;
    }

    public Learner(GooglePojo googlePojo) {
        super(googlePojo);
        this.firstName = googlePojo.getGiven_name();
        this.lastName = googlePojo.getFamily_name();
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "learnerID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<Notification> notifications = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "learnerID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<Transaction> transactions = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "learnerID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<CourseProgress> courseProgresses = new ArrayList<>();
}

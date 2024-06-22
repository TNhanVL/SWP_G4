/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TTNhan
 */
@Entity
@Table(name = "[Instructor]")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Instructor extends User {
    @Column(name = "instructorId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int organizationId;
    private String firstName;
    private String lastName;

    public Instructor(int ID, int organizationId, String picture, String username, String password, String email, String firstName, String lastName, int countryId, int status) {
        super(picture, username, password, email, countryId, status);
        this.ID = ID;
        this.organizationId = organizationId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Instructor(Learner learner) {
        super(learner);
        this.ID = learner.getID();
        this.firstName = learner.getFirstName();
        this.lastName = learner.getLastName();
    }

    public Instructor(Instructor instructor) {
        super(instructor);
        this.ID = instructor.ID;
        this.organizationId = instructor.organizationId;
        this.firstName = instructor.firstName;
        this.lastName = instructor.lastName;
    }

    @ManyToOne
    @JoinColumn(name = "organizationId", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Organization organization;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "instructorId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<Instruct> instructs = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "instructorId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author TTNhan
 */
@Entity
@Table(name = "[instructor]")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Instructor extends User {
    @Column(name = "instructorID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int organizationID;
    private String firstName;
    private String lastName;

    public Instructor(int ID, int organizationID, String picture, String username, String password, String email, String firstName, String lastName, int countryID, int status) {
        super(picture, username, password, email, countryID, status);
        this.ID = ID;
        this.organizationID = organizationID;
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
        this.organizationID = instructor.organizationID;
        this.firstName = instructor.firstName;
        this.lastName = instructor.lastName;
    }
}

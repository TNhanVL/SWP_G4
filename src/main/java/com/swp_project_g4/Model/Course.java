/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Model;

/**
 * @author TTNhan
 */

import jakarta.persistence.*;
import lombok.*;
import net.bytebuddy.build.ToStringPlugin;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Column(name = "courseID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int organizationID;
    private int instructorID;
    private String name;
    private String picture;
    private String description;
    private boolean verified = false;
    private int totalTime;
    private double price;
    private double rate;

    public Course(int ID, int organizationID, int instructorID, String name, String picture, String description, boolean verified, int totalTime, double price, double rate) {
        this.ID = ID;
        this.organizationID = organizationID;
        this.instructorID = instructorID;
        this.name = name;
        this.picture = picture;
        this.description = description;
        this.verified = verified;
        this.totalTime = totalTime;
        this.price = price;
        this.rate = rate;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "courseID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Chapter> chapters = new ArrayList<>();
}

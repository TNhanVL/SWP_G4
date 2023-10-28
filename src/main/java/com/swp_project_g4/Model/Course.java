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

}

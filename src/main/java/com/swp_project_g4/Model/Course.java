/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Model;

/**
 *
 * @author Thanh Duong
 */

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    private int courseID;
    private String name;
    private String image;
    private String description;
    private int organizationID;
    private int lecturerID;
    private double originPrice;
    private double price;
    private double rate;

}
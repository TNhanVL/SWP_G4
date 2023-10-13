/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Model;

import lombok.*;

/**
 *
 * @author Thanh Duong
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chapter {
    private int chapterID;
    private int courseID;
    private int index;
    private String name;
    private String description;
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Model;

import lombok.*;

/**
 *
 * @author TTNhan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private int ID;
    private String content;
    private int lessonID;
}

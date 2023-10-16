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
public class Lesson {

    private int lessonID;
    private int chapterID;
    private String name;
    private int index;
    private int type;
    private int time;

}

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
public class Question {

    private int questionID;
    private int lessonID;
    private int index;
    private String content;
    private int type;
    private int point;

}

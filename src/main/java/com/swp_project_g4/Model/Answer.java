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
public class Answer {
    private int answerID;
    private String content;
    private boolean correct;
    private int questionID;
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Model;

import lombok.*;

import java.util.Date;

/**
 *
 * @author TTNhan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResult {

    private int quizResultID;
    private int lessonID;
    private int userID;
    private Date startTime;
    private Date endTime;

}

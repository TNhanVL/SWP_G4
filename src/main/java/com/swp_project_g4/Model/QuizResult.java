/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 *
 * @author TTNhan
 */
@Entity
@Table(name = "quiz_result")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int quizResultID;
    private int lessonID;
    private int lessonProgressID;
    private int numberOfCorrectAnswer;
    private int numberOfQuestion;
    private int mark;
    private Date startAt;
    private Date endAt;

}

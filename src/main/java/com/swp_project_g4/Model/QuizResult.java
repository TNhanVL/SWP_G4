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
@Table(name = "quizResult")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int quizResultID;
    private int lessonID;
    private int lesson_progressID;
    private int number_of_correct_answer;
    private int number_of_question;
    private int mark;
    private int userID;
    private Date startAt;
    private Date endAt;

}

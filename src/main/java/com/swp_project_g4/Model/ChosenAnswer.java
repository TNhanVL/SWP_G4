/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Model;

import jakarta.persistence.*;
import lombok.*;

/**
 *
 * @author TTNhan
 */
//@Entity
//@Table(name = "chosenAnswer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChosenAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int quizResultID;
    private int questionID;
    private int answerID;
    private boolean correct;

}

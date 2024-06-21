/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author TTNhan
 */
@Entity
@Table(name = "ChosenAnswer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChosenAnswer {
    @Column(name = "chosenAnswerId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int quizResultID;
    private int questionId;
    private int answerId;
    private boolean correct;

    @ManyToOne
    @JoinColumn(name = "quizResultID", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private QuizResult quizResult;

    @ManyToOne
    @JoinColumn(name = "questionId", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Question question;

    @ManyToOne
    @JoinColumn(name = "answerId", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Answer answer;

}

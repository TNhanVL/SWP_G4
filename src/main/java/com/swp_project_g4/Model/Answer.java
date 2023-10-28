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
@Entity
@Table(name = "answer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @Column(name = "answerID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String content;
    private boolean correct;
    private int questionID;

    public Answer(int ID, String content, boolean correct, int questionID) {
        this.ID = ID;
        this.content = content;
        this.correct = correct;
        this.questionID = questionID;
    }

    @ManyToOne
    @JoinColumn(name = "questionID", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Question question;
}

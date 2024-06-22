/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TTNhan
 */
@Entity
@Table(name = "Quiz")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    @Column(name = "quizId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int lessonId;
    private String name;
    private String description;
    private int noQuestion;
    private boolean mustBeCompleted = true;
    private int percentToPassed = 80;

    public Quiz(int ID, int lessonId, String name, String description, int noQuestion, boolean mustBeCompleted, int percentToPassed) {
        this.ID = ID;
        this.lessonId = lessonId;
        this.name = name;
        this.description = description;
        this.percentToPassed = percentToPassed;
        this.mustBeCompleted = mustBeCompleted;
        this.noQuestion = noQuestion;
    }

    @ManyToOne
    @JoinColumn(name = "lessonId", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Lesson lesson;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "quizId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<Question> questions = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "quizId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<QuizResult> quizResults = new ArrayList<>();
}

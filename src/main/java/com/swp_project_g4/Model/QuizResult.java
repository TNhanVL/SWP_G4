/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author TTNhan
 */
@Entity
@Table(name = "QuizResult")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResult {
    @Column(name = "quizResultID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int lessonId;
    private int lessonProgressID;
    private int numberOfCorrectAnswer;
    private int numberOfQuestion;
    private int mark;
    private boolean finished;
    private Date startAt = new Date();
    private Date endAt;

    public QuizResult(int lessonId, int lessonProgressID, Lesson lesson) {
        this.lessonId = lessonId;
        this.lessonProgressID = lessonProgressID;
        endAt = new Date(startAt.getTime() + lesson.getTime() * 60 * 1000);
    }

    public QuizResult(int ID, int lessonId, int lessonProgressID, int numberOfCorrectAnswer, int numberOfQuestion, int mark, Date startAt, Date endAt) {
        this.ID = ID;
        this.lessonId = lessonId;
        this.lessonProgressID = lessonProgressID;
        this.numberOfCorrectAnswer = numberOfCorrectAnswer;
        this.numberOfQuestion = numberOfQuestion;
        this.mark = mark;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    @ManyToOne
    @JoinColumn(name = "lessonId", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "lessonProgressID", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private LessonProgress lessonProgress;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "quizResultID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<ChosenAnswer> chosenAnswers = new ArrayList<>();
}

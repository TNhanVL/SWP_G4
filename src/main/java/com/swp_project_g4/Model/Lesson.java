/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import net.bytebuddy.utility.nullability.NeverNull;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TTNhan
 */
@Entity
@Table(name = "Lesson")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    @Column(name = "lessonId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int chapterId;
    private Integer quizId;
    private String name = "";
    private String description;
    private int percentToPassed = 80;
    private boolean mustBeCompleted = true;
    private String content;
    private int type = 3;
    @Column(name = "[index]")
    private int index;
    private int time;

    public Lesson(int ID, int chapterId, Integer quizId, String name, String description, int percentToPassed, boolean mustBeCompleted, String content, int type, int index, int time) {
        this.ID = ID;
        this.chapterId = chapterId;
        this.quizId = quizId;
        this.name = name;
        this.description = description;
        this.percentToPassed = percentToPassed;
        this.mustBeCompleted = mustBeCompleted;
        this.content = content;
        this.type = type;
        this.index = index;
        this.time = time;
    }

    @ManyToOne
    @JoinColumn(name = "chapterId", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Chapter chapter;

    @ManyToOne
    @JoinColumn(name = "quizId", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Quiz quiz;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "lessonId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<LessonProgress> lessonProgresses = new ArrayList<>();
}

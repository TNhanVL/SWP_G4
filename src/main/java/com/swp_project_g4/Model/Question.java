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
import java.util.List;

/**
 * @author TTNhan
 */
@Entity
@Table(name = "question")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Column(name = "questionID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int lessonID;
    @Column(name = "[index]")
    private int index;
    private String content = "";
    private int type;
    private int point;

    public Question(int ID, int lessonID, int index, String content, int type, int point) {
        this.ID = ID;
        this.lessonID = lessonID;
        this.index = index;
        this.content = content;
        this.type = type;
        this.point = point;
    }

    @ManyToOne
    @JoinColumn(name = "lessonID", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Lesson lesson;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "questionID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<Answer> answers = new ArrayList<>();
}

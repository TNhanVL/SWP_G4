/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import net.bytebuddy.build.ToStringPlugin;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TTNhan
 */
@Entity
@Table(name = "chapter")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chapter {
    @Column(name = "chapterID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int courseID;
    @Column(name = "[index]")
    private int index;
    private String name = "";
    private String description = "";
    private int totalTime;

    public Chapter(int ID, int courseID, int index, String name, String description, int totalTime) {
        this.ID = ID;
        this.courseID = courseID;
        this.index = index;
        this.name = name;
        this.description = description;
        this.totalTime = totalTime;
    }

    @ManyToOne
    @JoinColumn(name = "courseID", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Course course;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "chapterID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<Lesson> lessons = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "chapterID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<ChapterProgress> chapterProgresses = new ArrayList<>();
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import net.bytebuddy.utility.nullability.NeverNull;

/**
 *
 * @author TTNhan
 */
@Entity
@Table(name = "lesson")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    @Column(name = "lessonID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int chapterID;
    private String name;
    private String description;
    private int percentToPassed;
    private boolean mustBeCompleted = false;
    private String content;
    private int type;
    @Column(name = "[index]")
    private int index;
    private int time;

}

package com.swp_project_g4.Model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author TTNhan
 */
@Entity
@Table(name = "[lessonProgress]")
public class LessonProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lessonProgressID;
    private int lessonID;
    private int chapterProgressID;
    private int progressPercent;
    private boolean completed;
    private Date startAt;

}

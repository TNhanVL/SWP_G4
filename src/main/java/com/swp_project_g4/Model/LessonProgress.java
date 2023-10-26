package com.swp_project_g4.Model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author TTNhan
 */
//@Entity
//@Table(name = "[lesson_progress]")
public class LessonProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lesson_progressID;
    private int lessonID;
    private int chapter_progressID;
    private int progress_percent;
    private boolean completed;
    private Date startAt;

}

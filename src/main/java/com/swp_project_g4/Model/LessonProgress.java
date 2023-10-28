package com.swp_project_g4.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author TTNhan
 */
@Entity
@Table(name = "[lesson_progress]")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonProgress {
    @Column(name = "lesson_progressID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int lessonID;
    private int chapter_progressID;
    private int progress_percent;
    private boolean completed;
    private Date startAt;

}

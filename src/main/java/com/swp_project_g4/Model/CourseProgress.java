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
@Table(name = "course_progress")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseProgressID;
    private int learnerID;
    private int courseID;
    private boolean enrolled = false;
    private int progressPercent;
    private boolean completed;
    private Date startAt;
    private boolean rated = false;
    private int rate;
}

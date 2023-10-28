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
    @Column(name = "courseProgressID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int learnerID;
    private int courseID;
    private boolean enrolled = false;
    private int progressPercent;
    private boolean completed;
    private Date startAt;
    private boolean rated = false;
    private int rate;

    public CourseProgress(int learnerID, int courseID) {
        this.learnerID = learnerID;
        this.courseID = courseID;
        this.startAt = new Date();
    }
}

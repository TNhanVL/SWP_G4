package com.swp_project_g4.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author TTNhan
 */
@Entity
@Table(name = "course_progress")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseProgress {
    @Column(name = "course_progressID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int learnerID;
    private int courseID;
    private boolean enrolled = false;
    private int progressPercent;
    private boolean completed;
    private Date startAt = new Date();
    private boolean rated = false;
    private int rate;

    public CourseProgress(int learnerID, int courseID) {
        this.learnerID = learnerID;
        this.courseID = courseID;
        this.startAt = new Date();
    }

    @ManyToOne
    @JoinColumn(name = "learnerID", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Learner learner;

    @ManyToOne
    @JoinColumn(name = "courseID", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Course course;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "courseProgressID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<ChapterProgress> chapterProgresses = new ArrayList<>();
}

package com.swp_project_g4.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private boolean completed = false;
    private boolean actionAfterCompleted = false;
    private int totalTime;
    private Date startAt = new Date();
    private boolean rated = false;
    private int rate;

    public CourseProgress(int learnerID, int courseID) {
        this.learnerID = learnerID;
        this.courseID = courseID;
        this.startAt = new Date();
    }

    @ManyToOne
    @JoinColumn(name = "learnerID", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Learner learner;

    @ManyToOne
    @JoinColumn(name = "courseID", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Course course;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "courseProgressID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<ChapterProgress> chapterProgresses = new ArrayList<>();
}

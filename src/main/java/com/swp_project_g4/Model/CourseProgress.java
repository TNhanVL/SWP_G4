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
@Table(name = "CourseProgress")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseProgress {
    @Column(name = "courseprogressid")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int learnerId;
    private int courseId;
    private boolean enrolled = false;
    private int progressPercent;
    private boolean completed = false;
    private boolean actionAfterCompleted = false;
    private int totalTime;
    private Date startAt = new Date();
    private boolean rated = false;
    private int rate;

    public CourseProgress(int learnerId, int courseId) {
        this.learnerId = learnerId;
        this.courseId = courseId;
        this.startAt = new Date();
    }

    @ManyToOne
    @JoinColumn(name = "learnerId", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Learner learner;

    @ManyToOne
    @JoinColumn(name = "courseId", insertable = false, updatable = false)
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

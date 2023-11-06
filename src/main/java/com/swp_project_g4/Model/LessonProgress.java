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
    private int chapterProgressID;
    private int progressPercent;
    private boolean completed;
    private Date startAt = new Date();

    public LessonProgress(int lessonID, int chapterProgressID) {
        this.lessonID = lessonID;
        this.chapterProgressID = chapterProgressID;
    }

    @ManyToOne
    @JoinColumn(name = "lessonID", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "chapterProgressID", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private ChapterProgress chapterProgress;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "lessonProgressID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<QuizResult> quizResults = new ArrayList<>();
}

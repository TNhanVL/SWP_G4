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
@Table(name = "[chapter_progress]")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterProgress {
    @Column(name = "chapter_progressID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int chapterID;
    private int courseProgressID;
    private int progressPercent;
    private boolean completed;
    private Date startAt = new Date();

    public ChapterProgress(int chapterID, int courseProgressID){
        this.chapterID = chapterID;
        this.courseProgressID = courseProgressID;
    }

    @ManyToOne
    @JoinColumn(name = "chapterID", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Chapter chapter;

    @ManyToOne
    @JoinColumn(name = "courseProgressID", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private CourseProgress courseProgress;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "chapterProgressID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<LessonProgress> lessonProgresses = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "chapterProgressID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Chapter> chapters = new ArrayList<>();
}

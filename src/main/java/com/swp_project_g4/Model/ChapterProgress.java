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
    private Boolean completed;
    private Date startAt;

    public ChapterProgress(int chapterID, int courseProgressID){
        this.chapterID = chapterID;
        this.courseProgressID = courseProgressID;
    }

}

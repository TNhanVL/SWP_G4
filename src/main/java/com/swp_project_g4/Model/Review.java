package com.swp_project_g4.Model;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "review")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Column(name = "reviewID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int instructorID;
    private int courseID;
    private boolean reviewed;
    private boolean verified;
    private String note;

    @ManyToOne
    @JoinColumn(name = "instructorID", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Instructor instructor;

    @ManyToOne
    @JoinColumn(name = "courseID", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Course course;
}

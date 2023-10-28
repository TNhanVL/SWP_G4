package com.swp_project_g4.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "instruct")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instruct {
    @Column(name = "intructID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int courseID;
    private int instructorID;

    @ManyToOne
    @JoinColumn(name = "courseID", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Course course;

    @ManyToOne
    @JoinColumn(name = "courseID", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Instructor instructor;
}

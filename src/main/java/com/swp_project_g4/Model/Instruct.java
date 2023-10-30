package com.swp_project_g4.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "instruct")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instruct {
    @Column(name = "instructID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int courseID;
    private int instructorID;

    @ManyToOne
    @JoinColumn(name = "courseID", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Course course;

    @ManyToOne
    @JoinColumn(name = "instructorID", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Instructor instructor;
}

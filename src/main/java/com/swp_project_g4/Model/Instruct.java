package com.swp_project_g4.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Instruct")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instruct {
    @Column(name = "instructId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int courseId;
    private int instructorId;

    @ManyToOne
    @JoinColumn(name = "courseId", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Course course;

    @ManyToOne
    @JoinColumn(name = "instructorId", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Instructor instructor;
}

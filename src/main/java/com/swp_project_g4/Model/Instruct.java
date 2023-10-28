package com.swp_project_g4.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "instruct")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instruct {
    @Column(name = "intructorID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int courseID;

    @ManyToOne
    @JoinColumn(name = "courseID", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Course course;
}

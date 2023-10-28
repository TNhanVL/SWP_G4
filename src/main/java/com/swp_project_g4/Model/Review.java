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
    private Boolean reviewed;
    private Boolean verified;
    private String note;
}

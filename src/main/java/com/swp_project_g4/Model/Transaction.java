package com.swp_project_g4.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "[transaction]")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Column(name = "transactionId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int learnerId;
    private int courseId;
    private double originPrice;
    private double price;
    private int type;
    private String description;
    private int status;

    @ManyToOne
    @JoinColumn(name = "learnerId", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Learner learner;

    @ManyToOne
    @JoinColumn(name = "courseId", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Course course;
}

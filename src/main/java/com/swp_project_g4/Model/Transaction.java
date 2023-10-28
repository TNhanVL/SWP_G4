package com.swp_project_g4.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "[transaction]")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Column(name = "transactionID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int learnerID;
    private int courseID;
    private double originPrice;
    private double price;
    private int type;
    private String description;
    private int status;

    @ManyToOne
    @JoinColumn(name = "learnerID", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Learner learner;

    @ManyToOne
    @JoinColumn(name = "courseID", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Course course;
}

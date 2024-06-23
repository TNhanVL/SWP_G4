package com.swp_project_g4.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Column(name = "cartId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int courseId;
    private int learnerId;

    public Cart(int courseId, int learnerId) {
        this.courseId = courseId;
        this.learnerId = learnerId;
    }

    @ManyToOne
    @JoinColumn(name = "courseId", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Course course;

    @ManyToOne
    @JoinColumn(name = "learnerId", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Learner learner;
}

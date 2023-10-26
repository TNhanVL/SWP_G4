package com.swp_project_g4.Model;

import jakarta.persistence.*;
import lombok.*;
//@Entity
//@Table(name = "review")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewID;
    private int instructorID;
    private int courseID;
    private boolean reviewed;
    private boolean verified;
    private String note;

    public Review(User userID, int courseID, boolean reviewed, boolean verified, String note) {
        super(userID);
        this.courseID = courseID;
        this.reviewed = reviewed;
        this.verified = verified;
        this.note = note;
    }
}

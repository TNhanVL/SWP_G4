package com.swp_project_g4.Model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review extends User {


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

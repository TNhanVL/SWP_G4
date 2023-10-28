package com.swp_project_g4.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 * @author TTNhan
 */
@Entity
@Table(name = "notification")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Column(name = "notificationID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int learnerID;
    private int type;
    private String description;
    @Column(name = "[read]")
    private boolean read = false;
    private Date receiveAt;

    @ManyToOne
    @JoinColumn(name = "learnerID", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Learner learner;
}

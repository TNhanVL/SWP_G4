package com.swp_project_g4.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "[transaction]")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionID;
    private int learnerID;
    private int courseID;
    private double originPrice;
    private double price;
    private int type;
    private String description;
    private int status;
}

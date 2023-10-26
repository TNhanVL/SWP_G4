package com.swp_project_g4.Model;

import jakarta.persistence.*;
import lombok.*;

//@Entity
//@Table(name = "instruct")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instruct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int intructorID;
    private int courseID;
}

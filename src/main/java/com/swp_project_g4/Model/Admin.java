
package com.swp_project_g4.Model;

import jakarta.persistence.*;
import lombok.*;

/**
 *
 * @author TTNhan
 */
@Entity
@Table(name = "Admin")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @Column(name = "adminId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String username;
    private String password;
    
}

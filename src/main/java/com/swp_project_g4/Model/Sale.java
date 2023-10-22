package com.swp_project_g4.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sale {
    private int courseID;
    private Double price;
    private Date startDate;
    private Date endDate;

}

package com.swp_project_g4.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sale {
 private int courseID;
 private Double price ;
 private String startDate;
 private String endDate;

}

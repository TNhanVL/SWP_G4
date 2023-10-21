package com.swp_project_g4.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends User  {

    private int course ;
    private float orginPrice;
    private float price;
    private int type;
    private String description ;
    private int status ;

    public Transaction(User user ,int course, float orginPrice, float price, int type, String description, int status) {
        super(user);
        this.course = course;
        this.orginPrice = orginPrice;
        this.price = price;
        this.type = type;
        this.description = description;
        this.status = status;
    }
}

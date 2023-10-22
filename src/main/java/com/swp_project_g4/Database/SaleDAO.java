package com.swp_project_g4.Database;

import com.swp_project_g4.Model.Sale;


import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaleDAO  extends DBConnection{


    public static boolean insertSale(Sale sale) {
        boolean result = false;
        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("insert into sale(courseID, price, startDate, endDate)\n" +
                    "values (?,?,?,?)");
//            statement.setInt(1,Review.getReviewID());
            statement.setInt(1,sale.getCourseID());
            statement.setDouble(2, sale.getPrice());
            statement.setString(3,sale.getStartDate());
            statement.setString(4,sale.getEndDate());
            statement.execute();
            //Indentify the last ID inserted
            //disconnect to database
            disconnect();

            result = true;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;

    }

    public static void main(String[] args) {
        Sale lect = new Sale(3,40.0,"14/12/2023","15/12/2023");
        insertSale(lect);
    }

}

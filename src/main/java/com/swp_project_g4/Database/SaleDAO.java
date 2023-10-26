package com.swp_project_g4.Database;

import com.swp_project_g4.Model.Instructor;
import com.swp_project_g4.Model.Sale;


import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaleDAO extends DBConnection {


    public static boolean insertSale(Sale sale) {
        boolean result = false;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("insert into sale(courseID, price, start_date, end_date)\n" +
                    "values (?,?,?,?)");
//            statement.setInt(1,Review.getReviewID());
            statement.setInt(1, sale.getCourseID());
            statement.setDouble(2, sale.getPrice());
            statement.setString(3, dateFormat.format(sale.getStartAt()));
            statement.setString(4, dateFormat.format(sale.getEndAt()));
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

    public static boolean updateSale(Sale sale) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        try {
            //connect to database
            connect();

            statement = conn.prepareStatement("UPDATE sale set price =?,start_date =?,end_date =?where courseID =?");
            statement.setDouble(1, sale.getPrice());
            statement.setString(2, dateFormat.format(sale.getStartAt()));
            statement.setString(3, dateFormat.format(sale.getEndAt()));
            statement.setInt(4, sale.getCourseID());
            statement.executeUpdate();

            //disconnect to database
            disconnect();
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    public static boolean deleteInstructor(int courseID) {
        try {

            connect();
            statement = conn.prepareStatement(" delete from sale where courseID =?");
            statement.setInt(1, courseID);
            statement.execute();
            disconnect();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void main(String[] args) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

            Sale lect = new Sale(3, 1, 40.0, dateFormat.parse("14-12-2023 12:15:20.000"), dateFormat.parse("15-12-2023 12:15:20.000"));
            System.out.println(lect);
            insertSale(lect);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

}

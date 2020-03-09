/*
Written By : Robert Watkin
Date Created : 31/10/2019
*/
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    public static void createTable(String url){
        String sql = "CREATE TABLE IF NOT EXISTS bookings(\n" +
                "bookingID integer PRIMARY KEY, \n" +
                "name string NOT NULL, \n" +
                "holidayDuration integer NOT NULL, \n" +
                "hotelRoom string NOT NULL, \n" +
                "subtotal float NOT NULL, \n" +
                "roomPrice float NOT NULL, \n" +
                "startDate string NOT NULL);";

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}

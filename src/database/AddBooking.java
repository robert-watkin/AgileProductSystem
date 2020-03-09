/*
Written By : Robert Watkin
Date Created : 31/10/2019
*/
package database;

import ProductSystemAgile.Window;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddBooking {
    public static void add(String name, int holidayDuration, String hotelRoom, float subtotal, float roomPrice, String startDate){
        String url = Window.getUrl();

        String sql = "INSERT INTO bookings(name, holidayDuration, hotelRoom, subtotal, roomPrice, startDate) VALUES(?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, name);
            pstmt.setInt(2, holidayDuration);
            pstmt.setString(3, hotelRoom);
            pstmt.setFloat(4, subtotal);
            pstmt.setFloat(5, roomPrice);
            pstmt.setString(6, startDate);
            pstmt.execute();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}

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
    public static void add(String product, int stockAmount, float price){
        String url = Window.getUrl();

        String sql = "INSERT INTO products(product, stockAmount, price) VALUES(?,?,?)";

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, product);
            pstmt.setInt(2, stockAmount);
            pstmt.setFloat(3, price);
            pstmt.execute();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
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

public class AddReview {
    public static void add(int productID, String name, String review){
        String url = Window.getUrl();

        String sql = "INSERT INTO reviews(productID, name, review) VALUES(?,?,?)";

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, productID);
            pstmt.setString(2, name);
            pstmt.setString(3, review);
            pstmt.execute();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
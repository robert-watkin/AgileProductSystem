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

public class AddProduct {
    public static void add(String product, int stockAmount, float price, int availability){
        String url = Window.getUrl();

        String sql = "INSERT INTO products(product, stockAmount, price, availability) VALUES(?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, product);
            pstmt.setInt(2, stockAmount);
            pstmt.setFloat(3, price);
            pstmt.setInt(4, availability);
            pstmt.execute();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void update(int id, String product, int stockAmount, float price, int availability){
        String url = Window.getUrl();

        String sql = "UPDATE products \n" +
                " SET product = '" + product +
                "', stockAmount = " + stockAmount +
                ", price = " + price +
                ", availability = '" + availability + "'\n" +
                "WHERE productID = '" + id + "';";

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.execute();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
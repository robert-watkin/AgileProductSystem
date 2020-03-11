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
        // PRODUCTS TABLE
        System.out.println("===================\nCreating Products Table...");
        String sql = "CREATE TABLE IF NOT EXISTS products(\n" +
                "productID integer PRIMARY KEY, \n" +
                "product string NOT NULL, \n" +
                "stockAmount integer NOT NULL, \n" +
                "price float NOT NULL, \n" +
                "availability bit NOT NULL, \n" +
                "image string NOT NULL);";

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()){
            stmt.execute(sql);
            System.out.println("Products table created successfully!");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        // REVIEWS TABLE
        System.out.println("===================\nCreating Reviews Table...");
        sql = "CREATE TABLE IF NOT EXISTS reviews(\n" +
                "reviewID integer PRIMARY KEY, \n" +
                "productID integer NOT NULL, \n" +
                "name string NOT NULL, \n" +
                "review string NOT NULL);";

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()){
            stmt.execute(sql);
            System.out.println("Products table created successfully!");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}

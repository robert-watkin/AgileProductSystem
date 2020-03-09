/*
Written By : Robert Watkin
Date Created : 31/10/2019
*/
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateDatabase {
    // the function below creates a database (if the database already exists, all previous data is still present)
    public static void createDatabase (String url){
        try (Connection conn = DriverManager.getConnection(url)){
            System.out.println("Database created");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }


}

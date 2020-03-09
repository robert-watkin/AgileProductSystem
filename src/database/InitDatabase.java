/*
Written By : Robert Watkin
Date Created : 31/10/2019
*/
package database;

public class InitDatabase {
    public static void initDB(String url){
        CreateDatabase.createDatabase(url);
        CreateTable.createTable(url);
    }
}

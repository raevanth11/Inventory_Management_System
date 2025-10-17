package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = System.getenv("DBURL");
    private static final String USER = System.getenv("USER");
    private static final String PASSWORD = System.getenv("PASSWORD");


    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // load driver
        } catch (ClassNotFoundException e) {
            System.err.println(" MySQL JDBC Driver not found!");
            e.printStackTrace();
        }
    }

    public static Connection getConnection()  {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Fallback default values
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/Inventory_Management_System?useSSL=false&serverTimezone=UTC";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "Raevanth@112004";

    private static final String URL = System.getenv("DBURL") != null ? System.getenv("DBURL") : DEFAULT_URL;
    private static final String USER = System.getenv("DBUSER") != null ? System.getenv("DBUSER") : DEFAULT_USER;
    private static final String PASSWORD = System.getenv("DBPASSWORD") != null ? System.getenv("DBPASSWORD") : DEFAULT_PASSWORD;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Load MySQL driver
            System.out.println("✅ MySQL Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ MySQL JDBC Driver not found!");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connected to the database successfully.");
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to connect to DB: " + e.getMessage(), e);
        }
    }
}

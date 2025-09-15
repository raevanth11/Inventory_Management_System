package org.example.util;
import java.sql.*;
public class DBconnection {
    private static final String URL = "jdbc:mysql://localhost:3306/Inventory_Management_System?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "inventory_user";
    private static final String PASSWORD = "inventory123";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}

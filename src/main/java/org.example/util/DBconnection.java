package org.example.util;
import java.sql.*;
public class DBconnection {
    private static final String URL ="";
    private static final String USER ="root";
    private static final String PASSWORD = "raevanth11";
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

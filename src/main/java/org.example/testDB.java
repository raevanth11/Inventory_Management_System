package org.example;

import org.example.util.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class testDB {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "Select * from products";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String category = rs.getString("category");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                System.out.println(id + " " + name + " " + category + " " + quantity + " " + price);
            }
            if (conn != null) {
                System.out.println("Connection successful!");
            } else {
                System.out.println("Failed to connect!");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
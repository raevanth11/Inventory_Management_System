package org.example.util;

import org.example.model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

    public static void saveProduct(Product product) {
        String sql = "INSERT INTO Products (name, category, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getCategory());
            pstmt.setInt(3, product.getQuantity());
            pstmt.setDouble(4, product.getPrice());
            pstmt.executeUpdate();
            System.out.println("Product saved successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to save product: " + e.getMessage());
        }
    }

    public static void updateQuantity(int productId, int newQuantity) {
        String sql = "UPDATE Products SET quantity = ? WHERE id = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newQuantity);
            pstmt.setInt(2, productId);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Quantity updated successfully!");
            } else {
                System.out.println("Product not found with ID: " + productId);
            }
        } catch (SQLException e) {
            System.out.println("Failed to update quantity: " + e.getMessage());
        }
    }

    public static boolean reduceQuantity(int productId, int amount) {
        String sql = "UPDATE Products SET quantity = quantity - ? WHERE id = ? AND quantity >= ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, amount);
            pstmt.setInt(2, productId);
            pstmt.setInt(3, amount);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Failed to reduce quantity: " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteProduct(int productId) {
        String sql = "DELETE FROM Products WHERE id = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            int rows = pstmt.executeUpdate();
            return rows > 0; // true if product existed and was deleted
        } catch (SQLException e) {
            System.out.println("Failed to delete product: " + e.getMessage());
            return false;
        }
    }

    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Products";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                );
                products.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch products: " + e.getMessage());
        }
        return products;
    }
}

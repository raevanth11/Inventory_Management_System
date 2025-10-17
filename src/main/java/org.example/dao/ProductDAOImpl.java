package org.example.dao;

import org.example.model.Product;
import java.sql.*;
import java.util.*;

public class ProductDAOImpl {
    private static Connection conn;

    public ProductDAOImpl() {
        this.conn = conn;
    }

    public static boolean addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO products (id, name, category, quantity, price) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, product.getId());
        stmt.setString(2, product.getName());
        stmt.setString(3, product.getCategory());
        stmt.setInt(4, product.getQuantity());
        stmt.setDouble(5, product.getPrice());
        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("✅ Product added successfully: " + product.getName());
            return true;
        } else {
            System.out.println("⚠️ Failed to add product: " + product.getName());
            return false;
        }
    }

    public static List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            products.add(new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getInt("quantity"),
                    rs.getDouble("price")
            ));
        }
        return products;
    }

    public static boolean deleteProduct(int id) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        int rows = stmt.executeUpdate();
        if (rows > 0) {
            System.out.println("🗑️ Product deleted successfully (ID: " + id + ")");
            return true;
        } else {
            System.out.println("❌ Product not found (ID: " + id + ")");
            return false;
        }
    }

    public static Product getProductById(int id) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getInt("quantity"),
                    rs.getDouble("price")
            );
        }
        return null;
    }

    public static void updateProduct(int id, int newQuantity) throws SQLException {
        String sql = "UPDATE products SET quantity = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, newQuantity);
        stmt.setInt(2, id);
        int rows = stmt.executeUpdate();
        if (rows > 0) {
            System.out.println("✅ Product quantity updated successfully!");
        } else {
            System.out.println("❌ Product with ID " + id + " not found.");
        }
    }
    public boolean updateProduct(Product product) throws SQLException {
        String sql = "UPDATE products SET name=?, category=?, quantity=?, price=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getCategory());
            ps.setInt(3, product.getQuantity());
            ps.setDouble(4, product.getPrice());
            ps.setInt(5, product.getId());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public static void reduceProduct(int id, int reduceBy) throws SQLException {
        String selectSql = "SELECT quantity FROM products WHERE id = ?";
        String updateSql = "UPDATE products SET quantity = ? WHERE id = ?";
        PreparedStatement selectStmt = conn.prepareStatement(selectSql);
        selectStmt.setInt(1, id);
        ResultSet rs = selectStmt.executeQuery();
        if (rs.next()) {
            int currentQty = rs.getInt("quantity");
            if (reduceBy <= 0) {
                System.out.println("⚠️ Reduction amount must be positive.");
                return;
            }
            if (reduceBy > currentQty) {
                System.out.println("⚠️ Cannot reduce more than available quantity.");
                return;
            }
            int newQty = currentQty - reduceBy;
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setInt(1, newQty);
            updateStmt.setInt(2, id);
            updateStmt.executeUpdate();
            System.out.println("✅ Product quantity reduced successfully!");
        } else {
            System.out.println("❌ Product with ID " + id + " not found.");
        }
    }

    public Product searchByName(String name) throws SQLException {
        String sql = "SELECT * FROM products WHERE name = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getInt("quantity"),
                    rs.getDouble("price")
            );
        } else {
            System.out.println("❌ No product found with name: " + name);
            return null;
        }
    }

    public List<Product> searchByCategory(String category) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, category);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            products.add(new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getInt("quantity"),
                    rs.getDouble("price")
            ));
        }
        return products;
    }

    public static List<Product> LowStockProducts(int threshold) throws SQLException {
        List<Product> lowStock = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE quantity <= ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, threshold);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            lowStock.add(new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getInt("quantity"),
                    rs.getDouble("price")
            ));
        }
        return lowStock;
    }
}

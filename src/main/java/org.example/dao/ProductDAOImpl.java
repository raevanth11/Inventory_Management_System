package org.example.dao;

import org.example.exception.ProductNotFoundException;
import org.example.model.Product;
import org.example.util.DBconnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl extends ProductDao {

    public static boolean addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO products (id, name, category, quantity, price) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, product.getId());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getCategory());
            stmt.setInt(4, product.getQuantity());
            stmt.setDouble(5, product.getPrice());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLException("Product with ID " + product.getId() + " already exists.", e);
        }
    }


    public static void reduceProduct(int id, int reduceBy) throws SQLException {
        String selectSql = "SELECT quantity FROM products WHERE id = ?";
        String updateSql = "UPDATE products SET quantity = ? WHERE id = ?";

        try (Connection conn = DBconnection.getConnection();
            PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
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
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setInt(1, newQty);
                    updateStmt.setInt(2, id);
                    updateStmt.executeUpdate();
                    System.out.println("✅ Product quantity reduced successfully!");
                }
            } else {
                System.out.println("❌ Product with ID " + id + " not found.");
            }
        }
    }

    public static List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                ));
            }
        }
        return products;
    }

    public Product searchByName(String name) throws SQLException, ProductNotFoundException {
        String sql = "SELECT * FROM products WHERE name = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Product product = new Product(rs.getInt("id"), rs.getString("name")
                        ,rs.getString("category"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"));
                return product;
            } else {
                throw new ProductNotFoundException("No product found with name: " + name);
            }
        }
    }

    public static Product getProductById(int id) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        }
        return null;
    }

    public static void updateProduct(int id, int newQuantity) throws SQLException {
        String sql = "UPDATE products SET quantity = ? WHERE id = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newQuantity);
            stmt.setInt(2, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Product quantity updated successfully!");
            } else {
                System.out.println("❌ Product with ID " + id + " not found.");
            }
        }
    }


    public static boolean deleteProduct(int id) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("🗑️ Product deleted successfully!");
                return true;
            } else {
                System.out.println("❌ Product with ID " + id + " not found.");
                return false;
            }
        }
    }

    public static List<Product> LowStockProducts(int threshold) throws SQLException {
        List<Product> lowStock = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE quantity <= ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        }
        return lowStock;
    }
}

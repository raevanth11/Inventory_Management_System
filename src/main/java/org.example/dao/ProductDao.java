package org.example.dao;

import org.example.model.Product;
import org.example.util.CSVHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class ProductDao {

    public static void saveProduct(Product product) throws SQLException {
        try {
            List<Product> products = CSVHelper.loadProducts();
            products.add(product);
            CSVHelper.saveProducts(products);
            System.out.println("Product saved successfully in CSV!");
        } catch (IOException e) {
            System.out.println("Error saving product: " + e.getMessage());
        }
    }
    public static void reduceProductQuantity(int id, int reduceBy) throws SQLException {
        try {
            List<Product> products = CSVHelper.loadProducts();
            boolean found = false;

            for (Product p : products) {
                if (p.getId() == id) {
                    if (reduceBy <= 0) {
                        System.out.println("Reduction amount must be positive.");
                        return;
                    }
                    if (reduceBy > p.getQuantity()) {
                        System.out.println("Cannot reduce more than available quantity.");
                        return;
                    }
                    p.setQuantity(p.getQuantity() - reduceBy);
                    found = true;
                    break;
                }
            }

            if (found) {
                CSVHelper.saveProducts(products);
                System.out.println("Product quantity reduced successfully!");
            } else {
                System.out.println("Product with ID " + id + " not found.");
            }

        } catch (IOException e) {
            System.out.println("Error updating product: " + e.getMessage());
        }
    }


    public static List<Product> getAllProducts()  throws SQLException{
        try {
            return CSVHelper.loadProducts();
        } catch (IOException e) {
            System.out.println("Error loading products: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static Product getProductById(int id)  throws SQLException{
        try {
            for (Product p : CSVHelper.loadProducts()) {
                if (p.getId() == id) return p;
            }
        } catch (IOException e) {
            System.out.println("Error reading products: " + e.getMessage());
        }
        return null;
    }

    public static void updateProductQuantity(int id, int newQuantity)  throws SQLException{
        try {
            List<Product> products = CSVHelper.loadProducts();
            boolean found = false;
            for (Product p : products) {
                if (p.getId() == id) {
                    p.setQuantity(newQuantity);
                    found = true;
                    break;
                }
            }
            if (found) {
                CSVHelper.saveProducts(products);
                System.out.println("Product quantity updated successfully!");
            } else {
                System.out.println("Product with ID " + id + " not found.");
            }
        } catch (IOException e) {
            System.out.println("Error updating product: " + e.getMessage());
        }
    }

    public static boolean deleteProduct(int id)  throws SQLException{
        try {
            List<Product> products = CSVHelper.loadProducts();
            boolean removed = products.removeIf(p -> p.getId() == id);
            if (removed) {
                CSVHelper.saveProducts(products);
                System.out.println("Product deleted successfully!");
            } else {
                System.out.println("Product with ID " + id + " not found.");
            }
        } catch (IOException e) {
            System.out.println("Error deleting product: " + e.getMessage());
        }
        return false;
    }

    public static List<Product> getLowStockProducts(int threshold)  throws SQLException{
        List<Product> lowStock = new ArrayList<>();
        try {
            for (Product p : CSVHelper.loadProducts()) {
                if (p.getQuantity() <= threshold) {
                    lowStock.add(p);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading products: " + e.getMessage());
        }
        return lowStock;
    }
}

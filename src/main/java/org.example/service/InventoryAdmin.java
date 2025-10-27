package org.example.service;

import org.example.dao.ProductDAOImpl;
import org.example.model.Product;

import java.util.List;

public class InventoryAdmin {
    private final ProductDAOImpl dao = new ProductDAOImpl();

    // Add Product
    public void addProduct(Product p) {
        dao.addProduct(p);
    }

    // Get all products
    public void getAllProducts() {
        List<Product> products = dao.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("⚠️ No products available.");
        } else {
            System.out.println("---- 📦 Product List ----");
            System.out.printf("%-5s %-15s %-15s %-10s %-10s %-10s%n",
                    "ID", "Name", "Category", "Quantity", "Price", "Threshold");
            System.out.println("--------------------------------------------------------------------------");
            products.forEach(p ->
                    System.out.printf("%-5d %-15s %-15s %-10d %-10.2f %-10d%n",
                            p.getId(), p.getName(), p.getCategory(),
                            p.getQuantity(), p.getPrice(), p.getThreshold()));
        }
    }

    // Get Product by ID
    public void getProductById(int id) {
        Product p = dao.getProductById(id);
        if (p != null) {
            System.out.println("Product Found:");
            p.display();
        } else {
            System.out.println("⚠️ Product not found!");
        }
    }

    // Get Products by Name
    public void getProductByName(String name) {
        List<Product> products = dao.getProductByName(name);
        if (products != null && !products.isEmpty()) {
            System.out.println("Products Found:");
            products.forEach(Product::display);
        } else {
            System.out.println("⚠️ Product not found!");
        }
    }

    // Get Products by Category
    public void getProductByCategory(String category) {
        List<Product> products = dao.getProductByCategory(category);
        if (products != null && !products.isEmpty()) {
            System.out.println("Products Found:");
            products.forEach(Product::display);
        } else {
            System.out.println("⚠️ Product not found!");
        }
    }

    // Get Products by Price Range
    public void getProductByPriceRange(double minPrice, double maxPrice) {
        List<Product> products = dao.getProductByPriceRange(minPrice, maxPrice);
        if (products != null && !products.isEmpty()) {
            System.out.println("Products Found:");
            products.forEach(Product::display);
        } else {
            System.out.println("⚠️ Product not found!");
        }
    }

    // Update Product
    public void updateProduct(int id, String name, String category, String quantity, String price, String threshold) {
        Product p = dao.getProductById(id);
        if (p == null) {
            System.out.println("⚠️ Product not found!");
            return;
        }

        try {
            if (name != null && !name.trim().isEmpty()) p.setName(name);
            if (category != null && !category.trim().isEmpty()) p.setCategory(category);
            if (quantity != null && !quantity.trim().isEmpty()) p.setQuantity(Integer.parseInt(quantity));
            if (price != null && !price.trim().isEmpty()) p.setPrice(Double.parseDouble(price));
            if (threshold != null && !threshold.trim().isEmpty()) p.setThreshold(Integer.parseInt(threshold));

            dao.updateProduct(p);
            System.out.println("✅ Product updated successfully!");
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Invalid number format for quantity, price, or threshold.");
        }
    }

    // Delete Product
    public void deleteProduct(int id) {
        boolean deleted = dao.deleteProduct(id);
        if (deleted) {
            System.out.println("✅ Product removed successfully!");
        } else {
            System.out.println("⚠️ No product found with that ID!");
        }
    }
}

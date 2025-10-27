package org.example.model;

import org.example.exception.InvalidProductException;

public class Product {
    private int id;
    private String name;
    private String category;
    private int quantity;
    private double price;
    private int threshold;

    // Constructor
    public Product(int id, String name, String category, int quantity, double price) {
        setId(id);
        setName(name);
        setCategory(category);
        setQuantity(quantity);
        setPrice(price);
        setThreshold(this.threshold);
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public int getThreshold() { return threshold; }

    // Setters with validations
    public void setId(int id) {
        if (id <= 0) throw new InvalidProductException("❌ ID must be greater than 0.");
        this.id = id;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) throw new InvalidProductException("❌ Name cannot be empty.");
        this.name = name;
    }

    public void setCategory(String category) {
        if (category == null || category.trim().isEmpty()) throw new InvalidProductException("❌ Category cannot be empty.");
        this.category = category;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) throw new InvalidProductException("❌ Quantity cannot be negative.");
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        if (price <= 0) throw new InvalidProductException("❌ Price must be greater than 0.");
        this.price = price;
    }

    public void setThreshold(int threshold) {
        if (threshold < 0) throw new InvalidProductException("❌ Threshold cannot be negative.");
        this.threshold = threshold;
    }

    // Stock value
    public double stockValue() {
        return quantity * price;
    }

    // Display method
    public void display() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Category: " + category);
        System.out.println("Quantity: " + quantity);
        System.out.println("Price: " + price);
        System.out.println("Threshold: " + threshold);
        System.out.println("Stock Value: " + stockValue());
        System.out.println();
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + category + " | " + quantity + " | " + price + " | " + threshold;
    }
}

package org.example.model;

import org.example.service.InventoryAdmin;
import org.example.service.UserService;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        UserService userService = new UserService();
        InventoryAdmin inventoryService = new InventoryAdmin();

        System.out.println("===== Inventory Management System =====");

        User loggedInUser = null;

        while (loggedInUser == null) {
            System.out.println("\n1. Login");
            System.out.println("2. Register");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Username: ");
                    String username = sc.nextLine();
                    System.out.print("Password: ");
                    String password = sc.nextLine();
                    loggedInUser = userService.login(username, password);
                    if (loggedInUser != null) {
                        System.out.println("✅ Login successful! Welcome, " + loggedInUser.getUsername());
                    } else {
                        System.out.println("❌ Invalid credentials.");
                    }
                    break;
                case "2":
                    System.out.print("Username: ");
                    String newUser = sc.nextLine();
                    System.out.print("Password: ");
                    String newPass = sc.nextLine();
                    System.out.print("Role (ADMIN/USER): ");
                    String role = sc.nextLine();
                    userService.register(newUser, newPass, role);
                    break;
                default:
                    System.out.println("⚠️ Invalid choice.");
            }
        }

        // Main Menu
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Add Product");
            System.out.println("2. View All Products");
            System.out.println("3. Search Product by ID");
            System.out.println("4. Search Product by Name");
            System.out.println("5. Update Product");
            System.out.println("6. Delete Product");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            String option = sc.nextLine();

            switch (option) {
                case "1":
                    try {
                        System.out.print("ID: ");
                        int id = Integer.parseInt(sc.nextLine());
                        System.out.print("Name: ");
                        String name = sc.nextLine();
                        System.out.print("Category: ");
                        String category = sc.nextLine();
                        System.out.print("Quantity: ");
                        int quantity = Integer.parseInt(sc.nextLine());
                        System.out.print("Price: ");
                        double price = Double.parseDouble(sc.nextLine());
                        System.out.print("Threshold: ");
                        int threshold = Integer.parseInt(sc.nextLine());
                        Product p = new Product(id, name, category, quantity, price);
                        inventoryService.addProduct(p);
                    } catch (Exception e) {
                        System.out.println("❌ Error adding product: " + e.getMessage());
                    }
                    break;
                case "2":
                    inventoryService.getAllProducts();
                    break;
                case "3":
                    System.out.print("Enter Product ID: ");
                    int searchId = Integer.parseInt(sc.nextLine());
                    inventoryService.getProductById(searchId);
                    break;
                case "4":
                    System.out.print("Enter Product Name: ");
                    String searchName = sc.nextLine();
                    inventoryService.getProductByName(searchName);
                    break;
                case "5":
                    try {
                        System.out.print("Product ID to update: ");
                        int updateId = Integer.parseInt(sc.nextLine());
                        System.out.print("New Name (leave blank to skip): ");
                        String newName = sc.nextLine();
                        System.out.print("New Category (leave blank to skip): ");
                        String newCategory = sc.nextLine();
                        System.out.print("New Quantity (leave blank to skip): ");
                        String newQuantity = sc.nextLine();
                        System.out.print("New Price (leave blank to skip): ");
                        String newPrice = sc.nextLine();
                        System.out.print("New Threshold (leave blank to skip): ");
                        String newThreshold = sc.nextLine();

                        inventoryService.updateProduct(updateId, newName, newCategory, newQuantity, newPrice, newThreshold);
                    } catch (Exception e) {
                        System.out.println("❌ Error updating product: " + e.getMessage());
                    }
                    break;
                case "6":
                    System.out.print("Product ID to delete: ");
                    int deleteId = Integer.parseInt(sc.nextLine());
                    inventoryService.deleteProduct(deleteId);
                    break;
                case "0":
                    System.out.println("👋 Exiting system. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("⚠️ Invalid option.");
            }
        }
    }
}

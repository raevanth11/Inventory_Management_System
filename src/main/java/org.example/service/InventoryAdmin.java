package org.example.service;
import org.example.dao.ProductDAOImpl;
import org.example.exception.InvalidQuantityException;
import org.example.exception.ProductNotFoundException;
import org.example.model.Product;
import org.example.util.CSVHelper;
import java.sql.SQLException;
import java.util.*;

import static org.example.dao.ProductDAOImpl.LowStockProducts;

public class InventoryAdmin {
    private static Scanner sc = new Scanner(System.in);
    private static ProductDAOImpl dao = new ProductDAOImpl();

    public static void main(String[] args) throws SQLException, ProductNotFoundException {
        while (true) {
            System.out.println();
            printMenu();
            int n = -1;
            try {
                n = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input.");
                sc.nextLine();
                continue;
            }
            switch (n) {
                case 1 -> addProduct();
                case 2 -> removeProduct();
                case 3 -> reStock();
                case 4 -> searchAvailability();
                case 5 -> availableProducts();
                case 6 -> reduceStock();
                case 7 -> getLowStockProducts();
                case 8 -> {
                    System.out.println("Exiting......");
                    return;
                }
                default -> System.out.println("Invalid Choice.");
            }
        }
    }

    private static void addProduct() throws SQLException {
        System.out.print("Enter Id : ");
        int id;
        try {
            id = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            sc.nextLine();
            return;
        }

        List<Product> products = dao.getAllProducts();
        for (Product p : products) {
            if (p.getId() == id) {
                System.out.println("A product already exists with ID " + id);
                return;
            }
        }

        sc.nextLine();
        System.out.print("Enter name of the product : ");
        String name = sc.nextLine();
        if (name.trim().isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        System.out.print("Enter category of the product : ");
        String category = sc.nextLine();
        if (category.trim().isEmpty()) {
            System.out.println("Category cannot be empty.");
            return;
        }

        int quantity;
        try {
            System.out.print("Enter quantity of the product : ");
            quantity = sc.nextInt();
            if (quantity <= 0) throw new InvalidQuantityException("Quantity must be greater than 0.");
        } catch (Exception e) {
            System.out.println("Invalid quantity: " + e.getMessage());
            sc.nextLine();
            return;
        }

        double price;
        try {
            System.out.print("Enter price of the product : ");
            price = sc.nextDouble();
            if (price <= 0) throw new InvalidQuantityException("Price must be greater than 0.");
        } catch (Exception e) {
            System.out.println("Invalid price: " + e.getMessage());
            sc.nextLine();
            return;
        }

        sc.nextLine();
        Product p = new Product(id, name, category, quantity, price);
        dao.addProduct(p);
        System.out.println("Product Added Successfully.");
    }

    private static void removeProduct() {
        System.out.print("Enter the Id of the product to be removed: ");
        int id;
        try {
            id = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            sc.nextLine();
            return;
        }
        try {
            boolean removed = dao.deleteProduct(id);
            if (!removed) throw new ProductNotFoundException("Product with ID " + id + " not found.");
            System.out.println("Product Removed Successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void reStock() throws SQLException {
        System.out.print("Enter the Id to be ReStocked : ");
        int id;
        try {
            id = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
            return;
        }

        Product existing = dao.getProductById(id);
        if (existing == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.print("Enter additional quantity : ");
        int newQuantity;
        try {
            newQuantity = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
            return;
        }
        if (newQuantity <= 0) {
            System.out.println("Quantity should be greater than 0.");
            return;
        }

        existing.setQuantity(existing.getQuantity() + newQuantity);
        int exQuantity = existing.getQuantity();
        dao.updateProduct(exQuantity, newQuantity);
        System.out.println("Product quantity updated.");
    }

    private static void reduceStock() throws SQLException {
        System.out.print("Enter the Id to reduce quantity of product : ");
        int id;
        try {
            id = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
            return;
        }

        Product existing = dao.getProductById(id);
        if (existing == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.print("Enter the quantity to be reduced: ");
        int redQuantity;
        try {
            redQuantity = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
            return;
        }
        dao.reduceProduct(id, redQuantity);
        if (redQuantity <= 0) {
            System.out.println("Quantity should be greater than 0.");
            return;
        }
        if (redQuantity > existing.getQuantity()) {
            System.out.println("Not enough stock. Available: " + existing.getQuantity());
            return;
        }

        existing.setQuantity(existing.getQuantity() - redQuantity);
        int exQuantity = existing.getQuantity();
        dao.updateProduct(exQuantity, redQuantity);
        System.out.println("Product quantity reduced.");
    }

    private static void searchAvailability() throws SQLException, ProductNotFoundException {
        sc.nextLine();
        System.out.print("Enter name of the product to check availability : ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Product name cannot be empty.");
            return;
        }

        List<Product> products = Collections.singletonList(dao.searchByName(name));
        if (products.isEmpty()) {
            System.out.println("Product not found.");
        } else {
            products.forEach(p -> {
                System.out.println("Match Found.....!!");
                System.out.println("Available Quantity : " + p.getQuantity());
            });
        }
    }

    private static void availableProducts() throws SQLException {
        List<Product> products = dao.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("There are no products in inventory.");
            return;
        }
        CSVHelper.printProductsTable(products);
    }
    public static void getLowStockProducts() throws SQLException, ProductNotFoundException {
        int minValue;
        try {
            System.out.print("Enter minimum stock threshold: ");
            minValue = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
            return;
        }
        List<Product> lowStockProducts = LowStockProducts(minValue);
        if (lowStockProducts.isEmpty()) {
            System.out.println("No products are low on stock.");
        } else {
            System.out.println("Low Stock Products:");
            for (Product p : lowStockProducts) {
                System.out.println(p);
            }
        }
    }

    private static void printMenu() {
        System.out.println("Inventory Management System......By Raevanth !!");
        System.out.println("Select Operations :");
        System.out.println("1. Add Product.");
        System.out.println("2. Remove Product.");
        System.out.println("3. ReStock.");
        System.out.println("4. Search Product Availability.");
        System.out.println("5. Show Available Products.");
        System.out.println("6. Reduce Quantity.");
        System.out.println("7. Get Lowest Stock Product.");
        System.out.println("8. To Exit.");
    }
}

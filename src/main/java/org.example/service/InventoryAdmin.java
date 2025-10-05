package org.example.service;
import org.example.dao.ProductDAOImpl;
import org.example.exception.*;
import org.example.model.Product;
import org.example.util.CSVHelper;
import java.sql.SQLException;
import java.util.*;
import static org.example.dao.ProductDAOImpl.LowStockProducts;

public class InventoryAdmin {
    private static final Scanner sc = new Scanner(System.in);
    private static final ProductDAOImpl dao = new ProductDAOImpl();

    public static void main(String[] args) throws SQLException {
        while (true) {
            System.out.println();
            printMenu();
            int n;
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
                case 3 -> addQuantity();
                case 4 -> reduceQuantity();
                case 5 -> allAvailableProducts();
                case 6 -> getLowestStockProducts();
                case 7 -> searchProductAvailability();
                case 8 -> searchProductsWithinPriceRange();
                case 9 -> {
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

        List<Product> products = ProductDAOImpl.getAllProducts();
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
        ProductDAOImpl.addProduct(p);
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
            boolean removed = ProductDAOImpl.deleteProduct(id);
            if (!removed) throw new ProductNotFoundException("Product with ID " + id + " not found.");
            System.out.println("Product Removed Successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addQuantity() throws SQLException {
        int id;
        try {
            System.out.print("Enter the Id to be ReStocked : ");
            id = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
            return;
        }

        Product existing = ProductDAOImpl.getProductById(id);
        if (existing == null) {
            System.out.println("Product not found.");
            return;
        }

        int newQuantity;
        try {
            System.out.print("Enter additional quantity : ");
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

        int updatedQuantity = existing.getQuantity() + newQuantity;
        existing.setQuantity(updatedQuantity);
        ProductDAOImpl.updateProduct(id, updatedQuantity);
        System.out.println("Product quantity updated.");
    }

    private static void reduceQuantity() throws SQLException {
        int id;
        try {
            System.out.print("Enter the Id to reduce quantity of product : ");
            id = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
            return;
        }

        Product existing = ProductDAOImpl.getProductById(id);
        if (existing == null) {
            System.out.println("Product not found.");
            return;
        }

        int redQuantity;
        try {
            System.out.print("Enter the quantity to be reduced: ");
            redQuantity = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
            return;
        }
        if (redQuantity <= 0) {
            System.out.println("Quantity should be greater than 0.");
            return;
        }
        if (redQuantity > existing.getQuantity()) {
            System.out.println("Not enough stock. Available: " + existing.getQuantity());
            return;
        }
        ProductDAOImpl.reduceProduct(id, redQuantity);
        System.out.println("Product quantity reduced.");
    }

    private static void searchProductAvailability() throws SQLException {
        sc.nextLine();
        System.out.print("Enter name of the product to check availability : ");
        String productName = sc.nextLine().trim();

        if (productName.isEmpty()) {
            System.out.println("Product name cannot be empty.");
            return;
        }

        try {
            Product product = dao.searchByName(productName);
            if (product == null) {
                System.out.println("Product not found in the inventory.");
                return;
            }
            System.out.println("Match Found.....!!");
            System.out.println("Available Quantity : " + product.getQuantity());
        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }


    private static void allAvailableProducts() throws SQLException {
        List<Product> products = ProductDAOImpl.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("There are no products in inventory.");
            return;
        }
        CSVHelper.printProductsTable(products);
    }

    private static void getLowestStockProducts() throws SQLException {
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
            CSVHelper.printProductsTable(lowStockProducts);
        }
    }

    private static void searchProductsWithinPriceRange() throws SQLException {
        double startingPrice;
        try{
            System.out.println("Enter a StartingPrice : ");
            startingPrice = sc.nextDouble();
        }catch(InputMismatchException e){
            System.out.println("Please enter a valid price.");
            return;
        }

        double endingPrice;
        try{
            System.out.println("Enter a EndingPrice : ");
            endingPrice = sc.nextDouble();
        }catch(InputMismatchException e){
            System.out.println("Please enter a valid price.");
            return;
        }

        List<Product> products = ProductDAOImpl.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("There are no products in inventory.");
            return;
        }

        List<Product> result = new ArrayList<>();
        for(Product p : products){
            if(p.getPrice() >= startingPrice && p.getPrice() <= endingPrice){
                result.add(p);
            }
        }

        if (result.isEmpty()) {
            System.out.println("No products found in this price range.");
        } else {
            CSVHelper.printProductsTable(result);
        }
    }

    private static void printMenu() {
        System.out.println("\nInventory Management System......By Raevanth !!");
        System.out.println("\nSelect Operations :");
        System.out.println("1. Add Product.");
        System.out.println("2. Remove Product.");
        System.out.println("3. Add Quantity.");
        System.out.println("4. Reduce Quantity.");
        System.out.println("5. Show All Available Products.");
        System.out.println("6. Get All Lowest Stock Products.");
        System.out.println("7. Search Product Availability.");
        System.out.println("8. Search Product By Price Range.");
        System.out.println("9. Exit From The Menu.");
    }
}

package org.example.service;
import org.example.model.Product;
import org.example.util.ProductDao;
import java.util.*;

public class InventoryAdmin {
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        while (true) {
            System.out.println();
            printMenu();
            int n = -1;
            try {
                n = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input.");
                System.out.println("Please enter a valid input.");
                sc.nextLine();
                continue;
            }
            switch (n) {
                case 1 -> addProduct();
                case 2 -> removeProduct();
                case 3 -> reStock();
                case 4 -> searchAvailability();
                case 5 -> availableProducts();
                case 6 -> {
                    System.out.println("Exiting......");
                    return;
                }
                default -> System.out.println("Invalid Choice.");
            }
        }
    }
    private static void addProduct(){
        System.out.print("Enter Id : ");
        int id ;
        try {
            id = sc.nextInt();
        }catch(InputMismatchException e){
            System.out.println("Invalid input.");
            System.out.println("please enter a valid input.");
            sc.nextLine();
            return;
        }
        List<Product> products = ProductDao.getAllProducts();
        for(Product p : products){
            if(p.getId() == id){
                System.out.println("A product already exits with the entered ID.");
                return;
            }
        }
        sc.nextLine();
        System.out.print("Enter name of the product : ");
        String name = sc.nextLine();
        if(name.trim().isEmpty()){
            System.out.println("Name cannot be empty.");
            return;
        }

        System.out.print("Enter category of the product : ");
        String category = sc.nextLine();
        if(category.trim().isEmpty()){
            System.out.println("Category cannot be empty.");
            return;
        }

        int quantity;
        while (true) {
            try {
                System.out.print("Enter quantity of the product : ");
                quantity = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a whole number.");
                sc.nextLine();
            }
        }
        if(quantity <= 0){
            System.out.println("Quantity should be greater than 0.");
            return;
        }
        double price;
        while (true) {
            try {
                System.out.print("Enter price of the product : ");
                price = sc.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number (e.g., 99.99).");
                sc.nextLine();
            }
        }
        if(price <= 0){
            System.out.println("Price should be greater than 0.");
            return;
        }
        sc.nextLine();
        Product p = new Product(id, name, category, quantity, price);
        System.out.println("Product Added Successfully.");
        ProductDao.saveProduct(p);
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

        boolean removed = ProductDao.deleteProduct(id);

        if (removed) {
            System.out.println("Product Removed Successfully.");
        } else {
            System.out.println("Product not found in the database.");
        }
    }

    private static void reStock(){
            System.out.print("Enter the Id to be ReStocked : ");
            int id = -1;
            try {
                id = sc.nextInt();
            }catch(InputMismatchException e){
                System.out.println("Invalid input.");
                System.out.println("Please enter a valid input.");
                return;
            }

            List<Product> products = ProductDao.getAllProducts();
            boolean found = false;
            for(Product p : products) {
                if (p.getId() == id){
                    found = true;
                    System.out.print("Enter additional quantity : ");
                    int newQuantity;
                    try{
                        newQuantity = sc.nextInt();
                    }catch(InputMismatchException e){
                        System.out.println("Invalid input.");
                        System.out.println("Please enter a valid input.");
                        sc.nextLine();
                        return;
                    }
                    if(newQuantity <= 0){
                        System.out.println("Quantity should be greater than 0.");
                        return;
                    }
                    int total = p.getQuantity() + newQuantity;
                    ProductDao.updateQuantity(id, total);
                    System.out.println("Product quantity updated.");
                    break;
                }
            }
            if(!found){
                System.out.println("Product not found.");
            }
        }
        private static void searchAvailability(){
            sc.nextLine();
            System.out.print("Enter name of the product to check availability : ");
            String name = sc.nextLine().trim();
            if(name.trim().isEmpty()){
                System.out.println("Product name cannot be empty.");
                return;
            }

            List<Product> products = ProductDao.getAllProducts();
            boolean found = false;
            for(Product p : products){
                if(p.getName().equalsIgnoreCase(name)){
                    System.out.println("Match Found.....!!");
                    System.out.println("Available Quantity : "+p.getQuantity());
                    found = true;
                    break;
                }
            }
            if(!found){
                System.out.println("Product not found.");
            }
        }
        private static void availableProducts(){
            List<Product> products = ProductDao.getAllProducts();
            if(products.isEmpty()){
                System.out.println("There are no products in inventory.");
                return;
            }
            for(Product p : products){
                System.out.println(p);
            }

        }
        private static void printMenu(){
            System.out.println("Inventory Management System......By Raevanth !!");
            System.out.println("Select Operations :");
            System.out.println("1. Add Product.");
            System.out.println("2. Remove Product.");
            System.out.println("3. ReStock.");
            System.out.println("4. Search Product Availability.");
            System.out.println("5. Show Available Products.");
            System.out.println("6. To Exit.");
        }
    }


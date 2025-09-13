package org.example.service;
import org.example.model.Product;
import java.util.*;

public class InventoryAdmin {
    private static Scanner sc = new Scanner(System.in);
    private static List<Product> list = new ArrayList<>();
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
        for(Product p : list){
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
            System.out.println("Quantityt should be greater than 0.");
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
            list.add(p);
            System.out.println("Product Added Successfully.");
        }
        private static void removeProduct(){
            System.out.print("Enter the Id of the product to be removed : ");
            int id ;
            try {
                id = sc.nextInt();
            }catch(InputMismatchException e){
                System.out.println("Invalid input.");
                System.out.println("Please enter a valid input.");
                sc.nextLine();
                return;
            }
            final int searchId = id;
            boolean removed = false;
            for(int i=0;i< list.size();i++){
                if(list.get(i).getId() == id){
                    list.remove(i);
                    removed = true;
                    break;
                }
            }
            if(removed){
                System.out.println("Product Removed Successfully.");
            }
            else {
                System.out.println("Product not found.");
            }
        }
        private static void reStock(){
            System.out.print("Enter the Id to be ReStocked ; ");
            int id = -1;
            try {
                id = sc.nextInt();
            }catch(InputMismatchException e){
                System.out.println("Invalid input.");
                System.out.println("Please enter a valid input.");
                return;
            }

            boolean found = false;
            for(Product p : list) {
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
                        System.out.println("Quanty should be greater then 0.");
                        return;
                    }
                    p.setQuantity(p.getQuantity() + newQuantity);
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
            String name = sc.nextLine();
            if(name.trim().isEmpty()){
                System.out.println("Product name cannot be empty.");
                return;
            }
            boolean found = false;
            for(Product p : list){
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
            if(list.isEmpty()){
                System.out.println("There is no products on inventory.");
                return;
            }
            System.out.println("Available products : ");
            for(Product k : list){
                System.out.println(k);
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


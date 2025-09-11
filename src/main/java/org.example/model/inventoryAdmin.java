package org.example.model;

import java.util.*;

public class inventoryAdmin {
    private static Scanner sc = new Scanner(System.in);
    public static List<product> list = new ArrayList<>();
    public static void main(String[] args) {
        while (true) {
            System.out.println();
            printMenu();
            int n = sc.nextInt();
            switch (n) {
                case 1 -> addProduct();
                case 2 -> removeProduct();
                case 3 -> reStock();
                case 4 -> searchAvailability();
                case 5 -> availableProducts();
                case 6 ->{
                    System.out.println("Exiting......");
                    return;
                }
                default -> System.out.println("Invalid Choice.");
            }
        }
    }
        private static void addProduct(){
            System.out.println("Enter Id : ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter name of the product : ");
            String name = sc.nextLine();
            System.out.println("Enter category of the product : ");
            String category = sc.nextLine();
            System.out.println("Enter quantity of the product : ");
            int quantity = sc.nextInt();
            System.out.println("Enter price of the product : ");
            double price = sc.nextDouble();
            product p = new product(id, name, category, quantity, price);
            list.add(p);
            System.out.println("Product Added Successfully.");
        }
        private static void removeProduct(){
            System.out.println("Enter the Id of the product to be removed : ");
            int id = sc.nextInt();

            boolean removed = false;
            for(int i=0;i<list.size();i++){
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
            System.out.println("Enter the Id to be ReStocked ; ");
            int id = sc.nextInt();

            System.out.println("Enter additional quantity : ");
            int newQuantity = sc.nextInt();
            boolean added = false;
            for(product p : list){
                if(p.getId() == id){
                    p.setQuantity(p.getQuantity() + newQuantity);
                    System.out.println("Product quantity updated.");
                    added = true;
                    break;
                }
            }
            if(!added){
                System.out.println("Product not found.");
            }
        }
        private static void searchAvailability(){
            sc.nextLine();
            System.out.println("Enter name of the product to check availability : ");
            String name = sc.nextLine();

            boolean found = false;
            for(product p : list){
                if(p.getName().equals(name)){
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
            for(product k : list){
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
        }
    }


package org.example.model;
import java.util.*;
public class App {
    public static void main(String[] args) {
        Product p1 = new Product(1, "TV", "Infotainment", 50, 5000);
        Product p2 = new Product(2, "Mobile", "Daily use", 100, 10000);
        Product p3 = new Product(3, "Air Conditioner", "Home Appliance", 25, 45000);
        Product p4 = new Product(4, "Dish Washer", "Kitchen Appliances", 10, 20000);
        Product p5 = new Product(5, "Wall Clock", "Home Appliance", 35, 1000);
        List<Product> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p3);
        list.add(p4);
        list.add(p5);
        for(Product t : list){
            System.out.println(t);
        }
    }
}

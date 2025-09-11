package org.example.model;
import java.util.*;
public class App {
    public static void main(String[] args) {
        product p1 = new product(1, "TV", "Infotainment", 50, 5000);
        product p2 = new product(2, "Mobile", "Daily use", 100, 10000);
        product p3 = new product(3, "Air Conditioner", "Home Appliance", 25, 45000);
        product p4 = new product(4, "Dish Washer", "Kitchen Appliances", 10, 20000);
        product p5 = new product(5, "Wall Clock", "Home Appliance", 35, 1000);
        List<product> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p3);
        list.add(p4);
        list.add(p5);
        for(product t : list){
            System.out.println(t);
        }
    }
}

package org.example.model;

public class Product {
    private int id;
    private String name;
    private String category;
    private int quantity;
    private double price;
    public Product(int id, String name, String category, int quantity, double price){
        this.id = id;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getCategory(){
        return category;
    }
    public int getQuantity(){
        return quantity;
    }
    public double getPrice(){
        return price;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public String toString(){
        return "id: " + id + ", name: " + name + ", category: " + category +
                ", quantity: " + quantity + ", price: " + price;
    }
}

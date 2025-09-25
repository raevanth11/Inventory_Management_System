package org.example.util;

import org.example.model.Product;
import java.io.*;
import java.util.*;

public class CSVHelper {
    private static final String FILE_NAME = "products.csv";


    public static void saveProducts(List<Product> products) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            writer.println("id,name,category,quantity,price");
            for (Product p : products) {
                writer.println(
                        p.getId() + "," +
                                p.getName() + "," +
                                p.getCategory() + "," +
                                p.getQuantity() + "," +
                                p.getPrice()
                );
            }
        }
    }
    public static List<Product> loadProducts() throws IOException {
        List<Product> products = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return products;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) { // skip header row
                    firstLine = false;
                    continue;
                }
                String[] values = line.split(",");
                if (values.length == 5) {
                    int id = Integer.parseInt(values[0]);
                    String name = values[1];
                    String category = values[2];
                    int quantity = Integer.parseInt(values[3]);
                    double price = Double.parseDouble(values[4]);

                    products.add(new Product(id, name, category, quantity, price));
                }
            }
        }
        return products;
    }
    public static void printProductsTable(List<Product> products) {
        System.out.printf("%-5s %-15s %-15s %-10s %-10s%n",
                "ID", "Name", "Category", "Quantity", "Price");
        System.out.println("-----------------------------------------------------------");

        for (Product p : products) {
            System.out.printf("%-5d %-15s %-15s %-10d %-10.2f%n",
                    p.getId(), p.getName(), p.getCategory(), p.getQuantity(), p.getPrice());
        }
    }
}

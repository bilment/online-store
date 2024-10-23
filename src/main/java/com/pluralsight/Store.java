package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Store {

    public static void main(String[] args) {
        // Initialize variables
        ArrayList<Product> inventory = new ArrayList<Product>();
        ArrayList<Product> cart = new ArrayList<Product>();
        double totalAmount = 0.0;

        // Load inventory from CSV file
        loadInventory("products.csv", inventory);

        // Create scanner to read user input
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        // Display menu and get user choice until they choose to exit
        while (choice != 3) {
            System.out.println("Welcome to the Online com.pluralsight.Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");

            choice = scanner.nextInt();
            scanner.nextLine();

            // Call the appropriate method based on user choice
            switch (choice) {
                case 1:
                    displayProducts(inventory, cart, scanner);
                    break;
                case 2:
                    displayCart(cart, scanner, totalAmount);
                    break;
                case 3:
                    System.out.println("Thank you for shopping with us!");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    public static void loadInventory(String fileName, ArrayList<Product> inventory) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {

                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String id = parts[0];
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);

                    Product product = new Product(id, name, price);
                    inventory.add(product);
                }
            }
            System.out.println("Loaded!");
        } catch (Exception e) {
            System.out.println("An error has occurred!");
            e.getMessage();
        }
    }


    public static void displayProducts(ArrayList<Product> inventory, ArrayList<Product> cart, Scanner scanner) {
        System.out.println("Available Products:");
        for (Product product : inventory) {
            System.out.println(product);
        }

        System.out.println("Enter the ID of the product you want to add, or type 'R' to return to the main menu:");
        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("r")) {
            return;
        }

        Product selectedProduct = findProductById(input, inventory);
        if (selectedProduct != null) {
            cart.add(selectedProduct);
            System.out.println("Product successfully added to the cart: " + selectedProduct.getName());
        } else {
            System.out.println("Product could not found. Please enter a valid ID.");
        }
    }

    public static void displayCart(ArrayList<Product> cart, Scanner scanner, double totalAmount) {
        if (cart.isEmpty()){
            System.out.println("Your cart doesn't have any chart: ");
            return;
        }

        System.out.println("Your Cart: ");
        totalAmount = 0;
        for (Product product : cart) {
            System.out.println(product.getId() + " | " + product.getName() + " | $" + product.getPrice());
            totalAmount += product.getPrice();
        }
        System.out.printf("Total Amount of Products: $%.2f%n", totalAmount);

        System.out.println("Would you like to remove any item from your cart? YES/NO ");

        String answer = scanner.nextLine().trim();

        if (answer.equalsIgnoreCase("yes")){
            System.out.println("Please enter product ID for removing from the cart: ");
            String productId = scanner.nextLine().trim();

            Product removeProduct = findProductById(productId, cart);

            if (removeProduct != null) {
                cart.remove(removeProduct);
                totalAmount -= removeProduct.getPrice();
                System.out.println(removeProduct.getName() + " has been removed.");
                System.out.printf("Total Amount of Products: $%.2f%n", totalAmount);

            } else {
                System.out.println("Product not found in cart.");
            }
        }
    }

    public static void checkOut(ArrayList<Product> cart, double totalAmount) {

        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("Check Out Summary: ");
        totalAmount = 0;
        for (Product product : cart) {
            totalAmount += product.getPrice();
        }
        System.out.printf("Total Amount: $%.2f%n", totalAmount);
    }

    public static Product findProductById(String id, ArrayList<Product> inventory) {
        for (Product product : inventory) {
            if (product.getId().equalsIgnoreCase(id)) {
                return product;
            }
        }
        return null;
    }
}
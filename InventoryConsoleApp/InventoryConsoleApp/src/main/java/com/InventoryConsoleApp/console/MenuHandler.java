package com.InventoryConsoleApp.console;

import com.InventoryConsoleApp.client.ProductClientApi;
import com.InventoryConsoleApp.input.ConsoleInputReader;

public class MenuHandler {
    private final ProductClientApi apiClient;
    private final ConsoleInputReader consoleInputReader;

    public MenuHandler() {
        this.apiClient = new ProductClientApi();
        this.consoleInputReader = new ConsoleInputReader();
    }

    public void showMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Product Console ---");
            System.out.println("1. Add Product");
            System.out.println("2. View All Products");
            System.out.println("3. View Product by ID");
            System.out.println("4. Update Product Info");
            System.out.println("5. Update Product Stock");
            System.out.println("0. Exit");

            int choice = consoleInputReader.inputInt("Choose an option: ");

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> viewAllProducts();
                case 3 -> viewProductById();
                case 4 -> updateProductInfo();
                case 5 -> updateProductStock();
                case 0 -> {
                    running = false;
                    System.out.println("Exiting. Goodbye!");
                }
                default -> System.out.println("Invalid choice, try again.");
            }
        }
    }

    private void addProduct() {
        try {
            int id = consoleInputReader.inputInt("Enter new product ID:");
            String name = consoleInputReader.inputLine("Enter product name:");
            int quantity = consoleInputReader.inputInt("Enter initial quantity:");
            apiClient.createProduct(id, name, quantity);
            System.out.println("Product added successfully.");
        } catch (Exception e) {
            System.out.println("Failed to add product: " + e.getMessage());
        }
    }

    private void viewAllProducts() {
        try {
            apiClient.viewAllProducts();
        } catch (Exception e) {
            System.out.println("Failed to fetch products: " + e.getMessage());
        }
    }

    private void viewProductById() {
        try {
            int id = consoleInputReader.inputInt("Enter product ID:");
            apiClient.getProductById(id);
        } catch (Exception e) {
            System.out.println("Failed to fetch product: " + e.getMessage());
        }
    }

    private void updateProductInfo() {
        try {
            int id = consoleInputReader.inputInt("Enter product ID to update:");
            String name = consoleInputReader.inputLine("Enter new name:");
            int quantity = consoleInputReader.inputInt("Enter new quantity:");
            apiClient.updateProduct(id, name, quantity);
            System.out.println("Product info updated successfully.");
        } catch (Exception e) {
            System.out.println("Failed to update product info: " + e.getMessage());
        }
    }

    private void updateProductStock() {
        try {
            int id = consoleInputReader.inputInt("Enter product ID:");
            int newStock = consoleInputReader.inputInt("Enter new stock quantity:");
            apiClient.updateStock(id, newStock);
            System.out.println("Product stock updated successfully.");
        } catch (Exception e) {
            System.out.println("Failed to update stock: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new MenuHandler().showMenu();
    }
}

package com.BankApp.controller;

import com.BankApp.helper.InputHelper;

public class MainController {
    InputHelper inputHelper;
    AuthController authController;

    public MainController(){
        authController = new AuthController();
        inputHelper = new InputHelper();
    }

    public void start(){
        System.out.println("Welcome to the Banking System!");
        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = inputHelper.inputInt();

            switch (choice) {
                case 1:
                    try {
                        authController.register();
                        authController.login();
                    }
                    catch(Exception excep){
                        System.out.println("User not Registered! Try again....");
                    }
                    break;
                case 2:
                    authController.login();
                    break;
                case 3:
                    System.out.println("Thank you for using the banking system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}

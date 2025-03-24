package com.BankApp.controller;

import com.BankApp.dto.LoginRequest;
import com.BankApp.dto.RegisterRequest;
import com.BankApp.exception.LoginException;
import com.BankApp.helper.InputHelper;
import com.BankApp.service.AuthService;

public class AuthController {

    AccountController accountController;
    AuthService authService;
    InputHelper inputHelper;

    public AuthController(){
        accountController  = new AccountController();
        inputHelper = new InputHelper();
        authService = new AuthService();
    }

    public void register() {
        System.out.println("\n--- User Registration ---");

        System.out.println("Select Account Type: ");
        System.out.println("1. Saving Account");
        System.out.println("2. Current Account");
        int accountChoice = inputHelper.inputInt("Enter your choice (1/2): ");
        String accountType = (accountChoice == 1) ? "SAVINGS" : "CURRENT";

        String username = inputHelper.inputLine("Enter Username: ");
        String password = inputHelper.inputLine("Enter Password: ");
        String fullName = inputHelper.inputLine("Enter Full Name: ");
        String email = inputHelper.inputLine("Enter Email: ");
        int pin = inputHelper.inputInt("Enter Pin: ");

        System.out.println("Printingh detials:");
        System.out.println("uysername "+username);
        System.out.println("password "+ password);
        RegisterRequest registerRequest = new RegisterRequest();

        registerRequest.setUsername(username);
        registerRequest.setPassword(password);
        registerRequest.setEmail(email);
        registerRequest.setFullName(fullName);
        registerRequest.setAccountType(accountType);
        registerRequest.setPin(pin);

        authService.register(registerRequest);

        login();
    }

    public void login(){
        System.out.println("\n--- User Login ---");
        String username = inputHelper.inputLine("Enter your username: ");
        String password = inputHelper.inputLine("Enter your password: ");

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        try {
            authService.login(loginRequest);
            accountController.accountMainMenu(username);
        }
        catch(LoginException excep){
            System.out.println(excep.getMessage());
        }
    }
}

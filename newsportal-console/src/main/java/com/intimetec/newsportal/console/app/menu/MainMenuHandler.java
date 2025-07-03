package com.intimetec.newsportal.console.app.menu;

import com.intimetec.newsportal.console.app.session.UserSession;
import com.intimetec.newsportal.console.dto.LoginRequestDTO;
import com.intimetec.newsportal.console.dto.LoginResponseDTO;
import com.intimetec.newsportal.console.input.AuthInputHandler;
import com.intimetec.newsportal.console.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MainMenuHandler {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthInputHandler authInputHandler;

    private final Scanner scanner = new Scanner(System.in);

    public MainMenuHandler(){
        authService = new AuthService();
        authInputHandler = new AuthInputHandler();
    }

    public void start() {
        while (true) {
            System.out.println("\nWelcome to the News Aggregator application. Please choose the options below.");
            System.out.println("1. Login");
            System.out.println("2. Sign up");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> {
                    LoginRequestDTO loginInput = authInputHandler.readLoginInput();
                    LoginResponseDTO response = authService.login(loginInput);
                    if("ADMIN".equalsIgnoreCase(response.getRole())){
                        AdminMenuHandler.start();
                    }
                    else{
                        UserMenuHandler.start();
                    }
                    Long userId = Long.parseLong(response.getId());
                    UserSession.setUserId(userId);
                }
                case "2" -> authService.signup(authInputHandler.readSignupInput());
                case "3" -> {
                    System.out.println("Exiting application. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
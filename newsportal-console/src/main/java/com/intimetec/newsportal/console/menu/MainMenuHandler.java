package com.intimetec.newsportal.console.menu;

import com.intimetec.newsportal.console.app.session.UserSession;
import com.intimetec.newsportal.console.dto.LoginRequestDTO;
import com.intimetec.newsportal.console.dto.LoginResponseDTO;
import com.intimetec.newsportal.console.dto.SignUpRequestDTO;
import com.intimetec.newsportal.console.exception.InvalidCredentialException;
import com.intimetec.newsportal.console.input.AuthInputHandler;
import com.intimetec.newsportal.console.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class MainMenuHandler {

    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthInputHandler authInputHandler;

    @Autowired
    private UserMenuHandler userMenuHandler;

    @Autowired
    private AdminMenuHandler adminMenuHandler;

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
                    try {
                        LoginRequestDTO loginInput = authInputHandler.readLoginInput();
                        LoginResponseDTO response = authService.login(loginInput);
                        Long userId = Long.parseLong(response.getId());
                        UserSession.setUserId(userId);

                        if ("ADMIN".equalsIgnoreCase(response.getRole())) {
                            adminMenuHandler.start();
                        } else {
                            userMenuHandler.start();
                        }
                    }
                    catch (InvalidCredentialException invalidCredentialException){
                        System.out.println(invalidCredentialException.getMessage());
                    }

                }
                case "2" -> {
                    try{
                        SignUpRequestDTO signUpRequestDTO = authInputHandler.readSignupInput();
                        authService.signup(signUpRequestDTO);
                    }
                    catch(InvalidCredentialException invalidCredentialException){
                        System.out.println(invalidCredentialException.getMessage());
                    }
                }
                case "3" -> {
                    System.out.println("Exiting application. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
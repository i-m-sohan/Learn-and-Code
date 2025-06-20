package com.intimetec.newsportal.console.input;

import com.intimetec.newsportal.console.dto.LoginRequestDTO;
import com.intimetec.newsportal.console.dto.SignUpRequestDTO;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AuthInputHandler {
    private final Scanner scanner = new Scanner(System.in);

    public LoginRequestDTO readLoginInput() {
        System.out.print("Enter username: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        return new LoginRequestDTO(email, password);
    }

    public SignUpRequestDTO readSignupInput() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role: ");
        String role = scanner.nextLine();
        return new SignUpRequestDTO(username, email, password, role);
    }
}

package com.intimetec.newsportal.console.input;

import com.intimetec.newsportal.console.dto.LoginRequestDTO;
import com.intimetec.newsportal.console.dto.SignUpRequestDTO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.util.Scanner;

@Service
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
        String email;
        while (true) {
            System.out.print("Enter email: ");
            email = scanner.nextLine();
            if (isValidEmail(email)) break;
            System.out.println("Invalid email format. Try again.");
        }

        String password = readPassword("Enter password: ");
        SignUpRequestDTO signUpRequestDTO =  new SignUpRequestDTO(username, email, password, "USER");
        return signUpRequestDTO;
    }

    private String readPassword(String prompt) {
        Console console = System.console();
        if (console != null) {
            char[] passwordChars = console.readPassword(prompt);
            return new String(passwordChars);
        } else {
            System.out.print(prompt);
            return scanner.nextLine();
        }
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

}

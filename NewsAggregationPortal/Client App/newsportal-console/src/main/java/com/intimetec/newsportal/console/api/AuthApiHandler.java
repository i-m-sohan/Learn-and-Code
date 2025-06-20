package com.intimetec.newsportal.console.api;

import java.io.InputStreamReader;

public class AuthApiHandler {
    public static void login() {
        String email = InputStreamReader.readEmail();
        String password = InputReader.readPassword();
        User user = AuthApi.login(email, password);
        if (user.getRole().equals("ADMIN")) {
            AdminMenu.start(user);
        } else {
            UserMenu.start(user);
        }
    }
}

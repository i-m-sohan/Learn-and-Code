package com.intimetec.newsportal.console.service;

import com.intimetec.newsportal.console.dto.LoginRequestDTO;
import com.intimetec.newsportal.console.dto.SignUpRequestDTO;
import com.intimetec.newsportal.console.utils.ApiUtilis;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.Map;

@Service
public class AuthService {

    private static final String BASE_URL = "http://localhost:8080/newsportal.intimetec.com/v1";

    public void login(LoginRequestDTO loginDto) {
        try {
            Map<String, String> body = Map.of(
                    "username", loginDto.getUsername(),
                    "password", loginDto.getPassword()
            );

            HttpResponse<String> response = ApiUtilis.post(BASE_URL + "/login", body);
            System.out.println(response.toString());
            System.out.println("✅ Login response: " + response.body());
        } catch (Exception e) {
            System.out.println("❌ Login failed: " + e.getMessage());
        }
    }

    public void signup(SignUpRequestDTO signUpDto) {
        try {
            Map<String, String> body = Map.of(
                    "username", signUpDto.getUsername(),
                    "email", signUpDto.getEmail(),
                    "password", signUpDto.getPassword(),
                    "role", signUpDto.getRole()
            );

            HttpResponse<String> response = ApiUtilis.post(BASE_URL + "/signup", body);
            System.out.println("✅ Signup response: " + response.body());
        } catch (Exception e) {
            System.out.println("❌ Signup failed: " + e.getMessage());
        }
    }
}
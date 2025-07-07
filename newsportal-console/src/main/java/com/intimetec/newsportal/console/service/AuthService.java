package com.intimetec.newsportal.console.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intimetec.newsportal.console.dto.LoginRequestDTO;
import com.intimetec.newsportal.console.dto.LoginResponseDTO;
import com.intimetec.newsportal.console.dto.SignUpRequestDTO;
import com.intimetec.newsportal.console.exception.InvalidCredentialException;
import com.intimetec.newsportal.console.utils.ApiUtilis;
import org.apache.catalina.authenticator.BasicAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.Map;

@Service
public class AuthService {

    private static final String BASE_URL = "http://localhost:8080/newsportal.intimetec.com/v1";

    @Autowired
    ObjectMapper objectMapper;

    public LoginResponseDTO login(LoginRequestDTO loginDto) {
        try {
            Map<String, String> body = Map.of(
                    "username", loginDto.getUsername(),
                    "password", loginDto.getPassword()
            );

            HttpResponse<String> response = ApiUtilis.post(BASE_URL + "/login", body);
            int statusCode = response.statusCode();
            if(statusCode != 200){
                throw new InvalidCredentialException("User Credential are Invalid");
            }
            System.out.println("Login Sucessful!");
            LoginResponseDTO loginResponseDTO = objectMapper.readValue(response.body(), LoginResponseDTO.class);
            return loginResponseDTO;
        }
        catch(InvalidCredentialException invalidCredentialException){
            throw invalidCredentialException;
        }
        catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
            return null;
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
            if(response.statusCode() != 201 ){
                throw new InvalidCredentialException("Invalid User Credential");
            }
            System.out.println("Signup Succesfull!");
        }
        catch(InvalidCredentialException invalidCredentialException){
            throw invalidCredentialException;
        }
        catch (Exception e) {
            System.out.println("Signup failed: " + e.getMessage());
        }
    }
}
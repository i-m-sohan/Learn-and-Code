package com.intimetec.newsportal.controller;

import com.intimetec.newsportal.dto.LoginRequestDTO;
import com.intimetec.newsportal.dto.SignUpRequestDTO;
import com.intimetec.newsportal.service.AuthService;
import org.aspectj.lang.annotation.DeclareError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.net.http.HttpResponse;
import java.util.Map;

@RestController
@RequestMapping("/newsportal.intimetec.com/v1")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequestDTO signUpRequestDTO){
        authService.registerUser(signUpRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "User registered successfully"));

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO){
        authService.loginUser(loginRequestDTO);
        return ResponseEntity.ok(Map.of("message", "Login successful"));
    }

}

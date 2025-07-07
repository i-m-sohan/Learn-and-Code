package com.intimetec.newsportal.controller;

import com.intimetec.newsportal.dto.LoginRequestDTO;
import com.intimetec.newsportal.dto.SignUpRequestDTO;
import com.intimetec.newsportal.model.Notification;
import com.intimetec.newsportal.model.User;
import com.intimetec.newsportal.service.AuthService;
import com.intimetec.newsportal.service.NotificationService;
import com.intimetec.newsportal.service.UserService;
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

    @Autowired
    UserService userService;

    @Autowired
    NotificationService notificationService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequestDTO signUpRequestDTO){
        authService.registerUser(signUpRequestDTO);
        User user = userService.getUserByUsername(signUpRequestDTO.getUsername());
        notificationService.createCategoryNotificationPreferences(user.getId());
        return new ResponseEntity<>(
                Map.of(
                        "message", "Signup successful, User Registered",
                        "role", user.getRole(),
                        "id",user.getId()
                ),HttpStatus.CREATED
        );

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO){
        authService.loginUser(loginRequestDTO);
        User user = userService.getUserByUsername(loginRequestDTO.getUsername());

        return ResponseEntity.ok(
                Map.of(
                        "message", "Login successful",
                        "role", user.getRole(),
                        "id",user.getId()
                )
        );
    }

}

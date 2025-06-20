package com.intimetec.newsportal.console.dto;

public class SignUpRequestDTO {
    private String username;
    private String email;
    private String password;
    private String role;

    public SignUpRequestDTO(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
}


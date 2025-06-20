package com.intimetec.newsportal.console.dto;


public class LoginResponseDTO {
    private String message;
    private String role;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}

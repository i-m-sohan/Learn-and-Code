package com.BankApp.dto;


public class RegisterRequestDTO {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String accountType;
    private int pin;
    public RegisterRequestDTO(){

    }
    public RegisterRequestDTO(String username, String password, String fullName, String email, String accountType, int pin) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.accountType = accountType;
        this.pin = pin;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getFullName() {
        return fullName;
    }
    public String getEmail() {
        return email;
    }
    public String getAccountType() {
        return accountType;
    }
    public int getPin(){
        return this.pin;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    public void setPin(int pin){
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", accountType='" + accountType + '\'' +
                ", pin=" + pin +
                '}';
    }
}

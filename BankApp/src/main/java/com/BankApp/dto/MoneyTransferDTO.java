package com.BankApp.dto;

public class MoneyTransferDTO {
    private String senderUsername;
    private String receiverAccountNumber;
    private int amount;
    private int pin;

    public MoneyTransferDTO(){

    }

    public MoneyTransferDTO(String senderUsername, String receiverAccountNumber, int amount, int pin) {
        this.senderUsername = senderUsername;
        this.receiverAccountNumber = receiverAccountNumber;
        this.amount = amount;
        this.pin = pin;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public void setReceiverAccountNumber(String receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }
}

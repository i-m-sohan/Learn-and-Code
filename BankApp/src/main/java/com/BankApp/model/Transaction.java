package com.BankApp.model;


import com.BankApp.service.TransactionService;

public class Transaction {
    private String fromAccount;
    private String toAccount;
    private int amount;
    private String timestamp;

    public Transaction(){

    }

    public Transaction(String fromAccount, String toAccount, int amount, String timestamp) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "fromAccount='" + fromAccount + '\'' +
                ", toAccount='" + toAccount + '\'' +
                ", amount=" + amount +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}


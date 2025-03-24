package com.BankApp.model;

public class Account {
    private String username;
    private String accountNumber;
    private int balance;
    private int transactionCount;
    private int interestEarned;
    private int pin;

    public Account() {
    }

    public Account( String username, String accountNumber, int balance, int transactionCount, int interestEarned,int pin) {
        this.username = username;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.transactionCount = transactionCount;
        this.interestEarned = interestEarned;
        this.pin = pin;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getBalance() {
        return balance;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }

    public int getInterestEarned() {
        return interestEarned;
    }

    public void setInterestEarned(int interestEarned) {
        this.interestEarned = interestEarned;
    }

    public void deposit(int depositAmount){
        if(depositAmount>0){
            balance += depositAmount;
        }
    }

    public int getPin(){
        return this.pin;
    }
    public void setPin(int pin){
        this.pin = pin;
    }
    public void withdraw(int amount) {
        if (isWithdrawAmountValid(amount)) {
            this.balance -= amount;
        } else {
            System.out.println(" Withdrawal amount is not valid!");
            System.out.println(" Current Balance: " + balance + "\n Withdrawal Amount: " + amount);
        }
    }

    protected boolean isWithdrawAmountValid(int amount) {
        return amount > 0 && amount <= balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", transactionCount=" + transactionCount +
                ", interestEarned=" + interestEarned +
                ", pin=" + pin +
                '}';
    }
}

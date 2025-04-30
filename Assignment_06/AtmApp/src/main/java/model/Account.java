package model;

import exception.DailyLimitExceededException;
import exception.InsufficientBalanceException;

public class Account {
    private String accountNumber;
    private double balance;
    private double dailyLimit;
    private double dailyWithdrawn;
    private String cardNumber;

    public Account(){

    }
    public Account(String accountNumber, double balance, double dailyLimit) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.dailyLimit = dailyLimit;
    }

    public void setCardNumber(String cardNumber){
        this.cardNumber = cardNumber;
    }

    public String getCardNumber(){
        return this.cardNumber;
    }

    public void setAccountNumber(String accountNumber){
        this.accountNumber = accountNumber;
    }
    public String getAccountNumber() { return accountNumber; }

    public void setBalance(double balance){
        this.balance = balance;
    }
    public double getBalance() { return balance; }

    public void setDailyLimit(double dailyLimit){
        this.dailyLimit = dailyLimit;
    }

    public double getDailyLimit() { return dailyLimit; }

    public void setDailyWithdrawn(double dailyWithdrawn){
        this.dailyWithdrawn = dailyWithdrawn;
    }
    public double getDailyWithdrawn() { return dailyWithdrawn; }

    public void withdraw(double amount) {
        if(isInSufficientBalance(amount)){
            String msg = "Account balance is insufficient, currenct account balance : "+getBalance();
            throw new InsufficientBalanceException(msg);
        }
        if(isDailyLimitExceeded(amount)){
            String msg = "Daily Limit Exceed "+", Todays Available Limit : " + (getDailyLimit()-getDailyWithdrawn());
            throw new DailyLimitExceededException(msg);
        }
        balance -= amount;
        dailyWithdrawn += amount;
    }

    private boolean isInSufficientBalance(double amount){
        if(amount > balance){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isDailyLimitExceeded(double amount){
        if(dailyLimit < dailyWithdrawn + amount){
            return true;
        }
        else{
            return false;
        }
    }
}

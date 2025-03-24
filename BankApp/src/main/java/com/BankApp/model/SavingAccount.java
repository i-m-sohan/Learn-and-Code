package com.BankApp.model;

public class SavingAccount extends Account {
    private static int WITHDRAWL_LIMIT = 8000;

    @Override
    protected boolean isWithdrawAmountValid(int amount){
        if(amount > 0 && amount <= getBalance() && amount <= WITHDRAWL_LIMIT){
            return true;
        }
        else{
            return false;
        }
    }
}

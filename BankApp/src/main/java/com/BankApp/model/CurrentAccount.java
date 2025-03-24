package com.BankApp.model;

public class CurrentAccount extends Account{
    private static final int OVERDRAFT_LIMIT = 3000;

    @Override
    protected boolean isWithdrawAmountValid(int amount){
        if(amount > 0 && amount <= getBalance() + OVERDRAFT_LIMIT){
            return true;
        }
        else{
            return false;
        }
    }
}

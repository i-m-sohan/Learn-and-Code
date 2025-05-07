package service;

import exception.ATMOutOfCashException;
import exception.DailyLimitExceededException;
import exception.InsufficientBalanceException;
import model.Account;

public class ATMService {
    private static double atmCash= 900000;

    public ATMService(){

    }
    public ATMService(double atmCash) {
        this.atmCash = atmCash;
    }

    public void withdraw(Account account, double amount) throws InsufficientBalanceException, DailyLimitExceededException {
        if (amount > atmCash) {
            throw new ATMOutOfCashException("ATM does not have enough cash.");
        }
        account.withdraw(amount);
        atmCash -= amount;
    }
}

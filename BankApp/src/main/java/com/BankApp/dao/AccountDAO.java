package com.BankApp.dao;

import com.BankApp.model.Account;

import java.util.HashMap;
import java.util.Map;

public class AccountDAO {
    private static Map<String, Account> accounts = new HashMap<>();
    private static Map<String, String> usernameToAccountNumber = new HashMap<>();

    public void insertAccount(Account account){
        accounts.put(account.getAccountNumber(),account);
        usernameToAccountNumber.put(account.getUsername(),account.getAccountNumber());
    }
    public void updateAccount(Account account){
        accounts.put(account.getAccountNumber(),account);
    }

    public Account getAccountByAcountNumber(String accountNumber){
        return accounts.get(accountNumber);
    }

    public Account getAccountByUsername(String username){
        return accounts.get(usernameToAccountNumber.get(username));
    }
}

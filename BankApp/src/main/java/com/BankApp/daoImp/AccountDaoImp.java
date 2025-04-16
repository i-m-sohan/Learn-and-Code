package com.BankApp.daoImp;

import com.BankApp.dao.AccountDAO;
import com.BankApp.model.Account;

import java.util.HashMap;
import java.util.Map;

public class AccountDaoImp implements AccountDAO {
    private static Map<String, Account> accounts = new HashMap<>();
    private static Map<String, String> usernameToAccountNumber = new HashMap<>();

    @Override
    public void insertAccount(Account account){
        accounts.put(account.getAccountNumber(),account);
        usernameToAccountNumber.put(account.getUsername(),account.getAccountNumber());
    }

    @Override
    public void updateAccount(Account account){
        accounts.put(account.getAccountNumber(),account);
    }

    @Override
    public Account getAccountByAcountNumber(String accountNumber){
        return accounts.get(accountNumber);
    }

    @Override
    public Account getAccountByUsername(String username){
        return accounts.get(usernameToAccountNumber.get(username));
    }
}

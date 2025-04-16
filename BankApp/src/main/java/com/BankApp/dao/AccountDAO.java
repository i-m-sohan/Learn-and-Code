package com.BankApp.dao;

import com.BankApp.model.Account;

import java.util.HashMap;
import java.util.Map;

public interface AccountDAO {

    public void insertAccount(Account account);

    public void updateAccount(Account account);

    public Account getAccountByAcountNumber(String accountNumber);

    public Account getAccountByUsername(String username);
}

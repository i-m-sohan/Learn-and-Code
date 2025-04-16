package com.BankApp.service;

import com.BankApp.model.Account;

import java.util.HashMap;
import java.util.Map;

public class AccountFactory {
    private static final String accountClassDefaultPath = "com.BankApp.model.";
    private static Map<String,String> accountRegistry = new HashMap<>();

    static{
        accountRegistry.put("saving","SavingAccount");
        accountRegistry.put("current","CurrentAccount");
    }

    public static Account getAccountInstace(String accountType) throws Exception{
        String accountClassName = accountClassDefaultPath + accountRegistry.get(accountType);

        Class<?> clazz = Class.forName(accountClassName);
        Object instance = clazz.getDeclaredConstructor().newInstance();
        return (Account)instance;
    }
}

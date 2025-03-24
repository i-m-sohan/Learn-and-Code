package com.BankApp.dao;

import com.BankApp.model.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransactionDAO {
    static private HashMap<String, List<Transaction>> transactions = new HashMap<String, List<Transaction>>();

    public void insertTransaction(Transaction transaction){
        if(!transactions.containsKey(transaction.getFromAccount())){
            transactions.put(transaction.getFromAccount(),new ArrayList<>());
        }
        transactions.get(transaction.getFromAccount()).add(transaction);

        if(!transactions.containsKey(transaction.getToAccount())){
            transactions.put(transaction.getToAccount(),new ArrayList<>());
        }
        transactions.get(transaction.getToAccount()).add(transaction);
    }

    public List<Transaction> getTransactionsByAccountNumber(String accountNumber){
        return transactions.get(accountNumber);
    }
}

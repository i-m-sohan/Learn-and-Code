package com.BankApp.dao;

import com.BankApp.model.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TransactionDAO {

    public void insertTransaction(Transaction transaction);

    public List<Transaction> getTransactionsByAccountNumber(String accountNumber);
}

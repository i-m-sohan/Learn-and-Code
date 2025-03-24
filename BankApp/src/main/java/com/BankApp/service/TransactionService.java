package com.BankApp.service;

import com.BankApp.dao.AccountDAO;
import com.BankApp.dao.TransactionDAO;
import com.BankApp.dao.UserDAO;
import com.BankApp.dto.MoneyTransferDTO;
import com.BankApp.model.Account;
import com.BankApp.model.Transaction;

import java.util.List;

public class TransactionService {
    UserDAO userDAO;
    AccountDAO accountDAO;
    TransactionDAO transactionDAO;

    public TransactionService() {
        this.userDAO = new UserDAO();
        this.accountDAO = new AccountDAO();
        this.transactionDAO = new TransactionDAO();
    }

    public TransactionService(UserDAO userDAO, AccountDAO accountDAO, TransactionDAO transactionDAO) {
        this.userDAO = userDAO;
        this.accountDAO = accountDAO;
        this.transactionDAO = transactionDAO;
    }

    public void createTransaction(MoneyTransferDTO moneyTransferDTO){
        Transaction transaction = createTransactionEntity(moneyTransferDTO);
        transactionDAO.insertTransaction(transaction);
    }

    private Transaction createTransactionEntity(MoneyTransferDTO moneyTransferDTO){
        Account senderAccount = accountDAO.getAccountByUsername(moneyTransferDTO.getSenderUsername());

        Transaction transaction = new Transaction();
        transaction.setAmount(moneyTransferDTO.getAmount());
        transaction.setToAccount(moneyTransferDTO.getReceiverAccountNumber());
        transaction.setFromAccount(senderAccount.getAccountNumber());
        transaction.setTimestamp(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return transaction;
    }

    public void viewTransactionHistory(String username){
        Account account = accountDAO.getAccountByUsername(username);
        List<Transaction> transactionList = transactionDAO.getTransactionsByAccountNumber(account.getAccountNumber());

        if(transactionList!=null) {
            for (Transaction transaction : transactionList) {
                System.out.println("-----Transaction------");
                System.out.println("From : "+  transaction.getFromAccount());
                System.out.println("To : " + transaction.getToAccount());
                System.out.println("Amount : " + transaction.getAmount());
            }
        }else{
            System.out.println("There is no transaction to show!");
        }
    }
}

package com.BankApp.service;

import com.BankApp.dao.AccountDAO;
import com.BankApp.dao.TransactionDAO;
import com.BankApp.dao.UserDAO;
import com.BankApp.dto.MoneyTransferDTO;
import com.BankApp.dto.RegisterRequest;
import com.BankApp.model.*;

import java.security.SecureRandom;

public class AccountService {
    UserDAO userDAO;
    AccountDAO accountDAO;
    TransactionDAO transactionDAO;
    TransactionService transactionService;

    public AccountService(){
        userDAO = new UserDAO();
        accountDAO = new AccountDAO();
        transactionDAO = new TransactionDAO();
        transactionService = new TransactionService();
    }
    public AccountService(UserDAO userDAO, AccountDAO accountDAO){
        this.userDAO = userDAO;
        this.accountDAO = accountDAO;
    }

    public void deposit(String username, int depositAmount,int enteredPin){

        Account account = accountDAO.getAccountByUsername(username);

        if(!isValidPin(account,enteredPin)){
            return;
        }
        account.deposit(depositAmount);
        account.setTransactionCount(account.getTransactionCount()+1);

        accountDAO.updateAccount(account);
    }

    public void withdraw(String username, int withdrawAmount,int enteredPin){
        Account account = accountDAO.getAccountByUsername(username);

        if(!isValidPin(account,enteredPin)){
            return;
        }
        account.withdraw(withdrawAmount);
        account.setTransactionCount(account.getTransactionCount()+1);

        accountDAO.updateAccount(account);
    }

    public void checkBalance(String accountNumber,int enteredPin){

        Account account = accountDAO.getAccountByUsername(accountNumber);
        account.toString();

        if(!isValidPin(account,enteredPin)){
            return;
        }
        System.out.println("Account Balance : "+ account.getBalance());
    }

    public void moneyTransfer(MoneyTransferDTO moneyTransferDTO){
        Account senderAccount = accountDAO.getAccountByUsername(moneyTransferDTO.getSenderUsername());
        Account recieverAccount = accountDAO.getAccountByAcountNumber(moneyTransferDTO.getReceiverAccountNumber());

        withdraw(senderAccount.getUsername(),moneyTransferDTO.getAmount(),moneyTransferDTO.getPin());
        deposit(recieverAccount.getUsername(),moneyTransferDTO.getAmount(),moneyTransferDTO.getPin());

        transactionService.createTransaction(moneyTransferDTO);
    }

    private boolean isValidPin(Account account, int enteredPin){
        if(account.getPin()!=enteredPin){
            System.out.println("Invalid Pin, Transaction failed!");
            return false;
        }
        return true;
    }
}

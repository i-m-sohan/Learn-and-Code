package com.BankApp.service;

import com.BankApp.dao.AccountDAO;
import com.BankApp.dao.TransactionDAO;
import com.BankApp.dao.UserDAO;
import com.BankApp.daoImp.AccountDaoImp;
import com.BankApp.daoImp.TransactionDaoImp;
import com.BankApp.daoImp.UserDaoImp;
import com.BankApp.dto.MoneyTransferDTO;
import com.BankApp.dto.RegisterRequestDTO;
import com.BankApp.model.*;

import java.security.SecureRandom;

public class AccountService {
    UserDAO userDAO;
    AccountDAO accountDAO;
    TransactionDAO transactionDAO;
    TransactionService transactionService;

    public AccountService(){
        userDAO = new UserDaoImp();
        accountDAO = new AccountDaoImp();
        transactionDAO = new TransactionDaoImp();
        transactionService = new TransactionService();
    }
    public AccountService(UserDAO userDAO, AccountDAO accountDAO, TransactionDAO transactionDAO){
        this.userDAO = userDAO;
        this.accountDAO = accountDAO;
    }

    public void addAccount(RegisterRequestDTO registerRequestBody) throws Exception{
        Account account = createAccountEntity(registerRequestBody);
        accountDAO.insertAccount(account);
    }

    private Account createAccountEntity(RegisterRequestDTO registerRequest) throws Exception{
        Account account ;
        if(registerRequest.getAccountType().toLowerCase()=="saving"){
            account = new SavingAccount();
        }
        else{
            account = new CurrentAccount();
        }
        account.setAccountNumber(String.valueOf(1000000000L + new SecureRandom().nextInt(900000000)));
        account.setInterestEarned(0);
        account.setTransactionCount(0);
        account.setPin(registerRequest.getPin());
        account.setUsername(registerRequest.getUsername());
        return account;
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

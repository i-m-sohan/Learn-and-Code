package com.BankApp.service;

import com.BankApp.controller.AccountController;
import com.BankApp.dao.AccountDAO;
import com.BankApp.dao.UserDAO;
import com.BankApp.dto.LoginRequest;
import com.BankApp.dto.RegisterRequest;
import com.BankApp.exception.LoginException;
import com.BankApp.model.Account;
import com.BankApp.model.CurrentAccount;
import com.BankApp.model.SavingAccount;
import com.BankApp.model.User;

import java.security.SecureRandom;

public class AuthService {

    private UserDAO userDAO;
    private AccountDAO accountDAO;
    private AccountService accountService;
    private AccountController accountController;

    public AuthService(){
        userDAO = new UserDAO();
        accountDAO = new AccountDAO();
        accountService = new AccountService();
        accountController = new AccountController();
    }
    public AuthService(UserDAO userDAO, AccountDAO accountDAO, AccountService accountService, AccountController accountController){
        this.userDAO = userDAO;
        this.accountDAO = accountDAO;
        this.accountService = accountService;
        this.accountController = accountController;
    }

    public void register(RegisterRequest registerRequestBody){
        User user = createUserEntity(registerRequestBody);
        userDAO.insertUser(user);

        Account account = createAccountEntity(registerRequestBody);
        account.toString();
        accountDAO.insertAccount(account);
    }

    private User createUserEntity(RegisterRequest registerRequest){
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        user.setFullName(registerRequest.getFullName());
        return user;
    }

    private Account createAccountEntity(RegisterRequest registerRequest){
        Account account;
        if(registerRequest.getAccountType()=="Saving"){
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

        System.out.println(account.getAccountNumber());
        return account;
    }

    public void login(LoginRequest loginRequestBody) throws LoginException{

        User user = userDAO.getUserByUsername(loginRequestBody.getUsername());

        if(!isLoginDetailValid(user,loginRequestBody)){
            throw new LoginException("Username/Password not Valid!!");
        }
    }

    boolean isLoginDetailValid(User user, LoginRequest loginRequestBody){
        if(user==null ||(user.getPassword()).compareTo(loginRequestBody.getPassword())!=0){
            return false;
        }
        return true;
    }
}

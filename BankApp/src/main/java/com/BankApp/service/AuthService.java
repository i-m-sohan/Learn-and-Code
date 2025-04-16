package com.BankApp.service;

import com.BankApp.controller.AccountController;
import com.BankApp.dao.AccountDAO;
import com.BankApp.dao.UserDAO;
import com.BankApp.daoImp.AccountDaoImp;
import com.BankApp.daoImp.UserDaoImp;
import com.BankApp.dto.LoginRequestDTO;
import com.BankApp.dto.RegisterRequestDTO;
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
        userDAO = new UserDaoImp();
        accountDAO = new AccountDaoImp();
        accountService = new AccountService();
        accountController = new AccountController();
    }

    public AuthService(UserDAO userDAO, AccountDAO accountDAO, AccountService accountService, AccountController accountController){
        this.userDAO = userDAO;
        this.accountDAO = accountDAO;
        this.accountService = accountService;
        this.accountController = accountController;
    }

    public void login(LoginRequestDTO loginRequestBody) throws LoginException{
        User user = userDAO.getUserByUsername(loginRequestBody.getUsername());

        if(!isLoginDetailValid(user,loginRequestBody)){
            throw new LoginException("Username/Password not Valid!!");
        }
    }

    private boolean isLoginDetailValid(User user, LoginRequestDTO loginRequestBody){
        if(user == null || (user.getPassword()).compareTo(loginRequestBody.getPassword())!=0){
            return false;
        }
        return true;
    }
}

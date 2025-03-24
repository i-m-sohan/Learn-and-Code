package com.BankApp;

import com.BankApp.controller.MainController;
import com.BankApp.dao.AccountDAO;
import com.BankApp.dao.UserDAO;
import com.BankApp.model.Account;
import com.BankApp.model.User;

public class BankApp {

    public static void initializeValues(){
        User dummyUser = new User("Sohan Kumar", "sohan@example.com", "sohan", "root");
        Account dummyAccount = new Account("sohan", "ACC123456", 5000, 10, 200,1234);

        UserDAO userDAO = new UserDAO();
        userDAO.insertUser(dummyUser);

        AccountDAO accountDAO = new AccountDAO();
        accountDAO.insertAccount(dummyAccount);
    }

    public static void main(String[] args){
        initializeValues();

        MainController mainController = new MainController();
        mainController.start();
    }
}

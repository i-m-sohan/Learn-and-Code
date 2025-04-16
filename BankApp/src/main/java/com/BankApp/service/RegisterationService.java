package com.BankApp.service;

import com.BankApp.dto.RegisterRequestDTO;

public class RegisterationService {

    UserService userService;
    AccountService accountService;

    public RegisterationService(){
        userService = new UserService();
        accountService = new AccountService();
    }

    public void register(RegisterRequestDTO registerRequestBody) throws Exception{
        userService.addUser(registerRequestBody);
        accountService.addAccount(registerRequestBody);
    }
}

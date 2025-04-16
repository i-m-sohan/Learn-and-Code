package com.BankApp.service;

import com.BankApp.dao.UserDAO;
import com.BankApp.daoImp.UserDaoImp;
import com.BankApp.dto.RegisterRequestDTO;
import com.BankApp.model.User;

public class UserService {

    UserDAO userDAO;

    public UserService(){
        userDAO = new UserDaoImp();
    }

    public void addUser(RegisterRequestDTO registerRequestBody){
        User user = createUserEntity(registerRequestBody);
        userDAO.insertUser(user);
    }

    private User createUserEntity(RegisterRequestDTO registerRequestBody){
        User user = new User();
        user.setUsername(registerRequestBody.getUsername());
        user.setEmail(registerRequestBody.getEmail());
        user.setPassword(registerRequestBody.getPassword());
        user.setFullName(registerRequestBody.getFullName());
        return user;
    }
}

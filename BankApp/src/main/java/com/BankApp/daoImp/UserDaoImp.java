package com.BankApp.daoImp;

import com.BankApp.dao.UserDAO;
import com.BankApp.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserDaoImp implements UserDAO {
    private static Map<String, User> users = new HashMap<>();

    @Override
    public void insertUser(User user) {
        users.put(user.getUsername(),user);
    }

    @Override
    public User getUserByUsername(String username){
        return users.get(username);
    }
}

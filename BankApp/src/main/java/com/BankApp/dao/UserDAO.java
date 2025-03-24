package com.BankApp.dao;

import com.BankApp.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO {
    private static Map<String,User> users = new HashMap<>();

    public void insertUser(User user) {
        users.put(user.getUsername(),user);
    }

    public User getUserByUsername(String username){
        return users.get(username);
    }
}

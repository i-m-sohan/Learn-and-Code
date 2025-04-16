package com.BankApp.dao;

import com.BankApp.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserDAO {

    public void insertUser(User user);

    public User getUserByUsername(String username);
}

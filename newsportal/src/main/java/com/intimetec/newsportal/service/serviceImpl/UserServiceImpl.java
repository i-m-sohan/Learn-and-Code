package com.intimetec.newsportal.service.serviceImpl;

import com.intimetec.newsportal.model.User;
import com.intimetec.newsportal.repository.UserRepository;
import com.intimetec.newsportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserByUsername(String username){
        Optional<User> user =  userRepository.findByUsername(username);
        return user.get();
    }
}

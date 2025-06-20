package com.intimetec.newsportal.service;

import com.intimetec.newsportal.dto.LoginRequestDTO;
import com.intimetec.newsportal.dto.SignUpRequestDTO;

public interface AuthService {
    public void registerUser(SignUpRequestDTO signUpRequestDTO);
    public void loginUser(LoginRequestDTO loginRequestDTO);
}

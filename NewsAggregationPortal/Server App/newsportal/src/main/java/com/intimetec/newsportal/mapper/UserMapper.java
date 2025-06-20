package com.intimetec.newsportal.mapper;

import com.intimetec.newsportal.dto.SignUpRequestDTO;
import com.intimetec.newsportal.model.Role;
import com.intimetec.newsportal.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {

    public static User toEntity(SignUpRequestDTO signUpRequestDTO) {
        Role roleEnum = Role.valueOf(signUpRequestDTO.getRole().toUpperCase());

        User user = new User();
        user.setUsername(signUpRequestDTO.getUsername());
        user.setPassword(signUpRequestDTO.getPassword());
        user.setEmail(signUpRequestDTO.getEmail());
        user.setRole(roleEnum.name());
        return user;
    }
}

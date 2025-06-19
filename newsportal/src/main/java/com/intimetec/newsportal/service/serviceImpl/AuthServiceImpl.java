package com.intimetec.newsportal.service.serviceImpl;

import com.intimetec.newsportal.dto.LoginRequestDTO;
import com.intimetec.newsportal.dto.SignUpRequestDTO;
import com.intimetec.newsportal.exception.UserAlreadyExistException;
import com.intimetec.newsportal.exception.UserNotFoundException;
import com.intimetec.newsportal.model.User;
import com.intimetec.newsportal.repository.UserRepository;
import com.intimetec.newsportal.service.AuthService;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(SignUpRequestDTO signUpRequestDTO){
        Optional<User> oldUser = userRepository.findByUsername(signUpRequestDTO.getUsername());
        if(oldUser.isPresent()){
            throw new UserAlreadyExistException("User with username : "+ oldUser.get().getUsername() +" and Email : " + oldUser.get().getEmail() + " already exist");
        }

        String hashedPassword = passwordEncoder.encode(signUpRequestDTO.getPassword());

        User user = new User();
        user.setUsername(signUpRequestDTO.getUsername());
        user.setEmail(signUpRequestDTO.getEmail());
        user.setRole(signUpRequestDTO.getRole());
        user.setPassword(hashedPassword);

        userRepository.save(user);
    }

    @Override
    public void loginUser(LoginRequestDTO loginRequestDTO){

        Optional<User> oldUser = userRepository.findByUsername(loginRequestDTO.getUsername());

        if(!oldUser.isPresent()){
            throw new UserNotFoundException("User with username : "+ loginRequestDTO.getUsername() +" not found!");
        }

        if(!passwordEncoder.matches(loginRequestDTO.getPassword(),oldUser.get().getPassword())){
            throw new BadCredentialsException("Invalid Credential!");
        }
    }
}

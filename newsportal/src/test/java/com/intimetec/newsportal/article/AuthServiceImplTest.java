package com.intimetec.newsportal.article;

import com.intimetec.newsportal.dto.LoginRequestDTO;
import com.intimetec.newsportal.dto.SignUpRequestDTO;
import com.intimetec.newsportal.exception.UserAlreadyExistException;
import com.intimetec.newsportal.exception.UserNotFoundException;
import com.intimetec.newsportal.model.User;
import com.intimetec.newsportal.repository.UserRepository;
import com.intimetec.newsportal.service.serviceImpl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private SignUpRequestDTO getValidSignUpRequest() {
        return new SignUpRequestDTO("john", "john@example.com", "password123", "USER");
    }

    private LoginRequestDTO getValidLoginRequest() {
        return new LoginRequestDTO("john", "password123");
    }

    @Test
    void registerUser_shouldThrowIfUserExists() {
        SignUpRequestDTO dto = getValidSignUpRequest();
        when(userRepository.findByUsername(dto.getUsername())).thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistException.class, () -> authService.registerUser(dto));
    }

    @Test
    void registerUser_shouldThrowIfRoleInvalid() {
        SignUpRequestDTO dto = new SignUpRequestDTO("john", "john@example.com", "pass", "WRONG_ROLE");

        when(userRepository.findByUsername(dto.getUsername())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> authService.registerUser(dto));
    }

    @Test
    void loginUser_shouldSucceedIfValid() {
        LoginRequestDTO dto = getValidLoginRequest();
        User user = new User();
        user.setPassword("hashed123");

        when(userRepository.findByUsername(dto.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(dto.getPassword(), "hashed123")).thenReturn(true);

        assertDoesNotThrow(() -> authService.loginUser(dto));
    }

    @Test
    void loginUser_shouldThrowIfUserNotFound() {
        LoginRequestDTO dto = getValidLoginRequest();
        when(userRepository.findByUsername(dto.getUsername())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> authService.loginUser(dto));
    }

    @Test
    void loginUser_shouldThrowIfPasswordInvalid() {
        LoginRequestDTO dto = getValidLoginRequest();
        User user = new User();
        user.setPassword("hashed123");

        when(userRepository.findByUsername(dto.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(dto.getPassword(), "hashed123")).thenReturn(false);

        assertThrows(BadCredentialsException.class, () -> authService.loginUser(dto));
    }
}


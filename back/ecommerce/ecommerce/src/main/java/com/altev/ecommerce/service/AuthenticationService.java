package com.altev.ecommerce.service;


import com.altev.ecommerce.dao.UserRepository;
import com.altev.ecommerce.dto.LoginRequest;
import com.altev.ecommerce.dto.SignupRequest;
import com.altev.ecommerce.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
        UserRepository userRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public User signup(SignupRequest input) {
        User user = new User();
        user.setFirstname(input.getFirstname());
        user.setUsername(input.getFirstname());
        user.setEmail(input.getEmail());
        user .setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(user);
    }

    public User authenticate(LoginRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
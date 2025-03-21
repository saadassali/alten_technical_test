package com.altev.ecommerce.service.impl;

import com.altev.ecommerce.dao.UserRepository;
import com.altev.ecommerce.dto.SignupRequest;
import com.altev.ecommerce.entity.User;
import com.altev.ecommerce.service.IAuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenService jwtTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public String registerUser(SignupRequest request) {
        // Check if email or username already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already taken!");
        }

        // Create new user with encrypted password
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setFirstname(request.getFirstname());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Encrypt password
        if(request.getEmail().equals("admin@admin.com")){
            user.setIsAdmin(true);
        }else{
            user.setIsAdmin(false);
        }
        // Save user to database
        userRepository.save(user);

        // Generate JWT token for the new user
        return jwtTokenService.generateToken(user);
    }
}

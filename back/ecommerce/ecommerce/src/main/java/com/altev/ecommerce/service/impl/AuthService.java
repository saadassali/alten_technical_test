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
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already taken!");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setUsername(request.username());
        user.setFirstname(request.firstname());
        user.setPassword(passwordEncoder.encode(request.password()));
        if(request.email().equals("admin@admin.com")){
            user.setIsAdmin(true);
        }else{
            user.setIsAdmin(false);
        }
        userRepository.save(user);

        return jwtTokenService.generateToken(user);
    }
}

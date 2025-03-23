package com.altev.ecommerce.controller;

import com.altev.ecommerce.dto.JwtResponse;
import com.altev.ecommerce.dto.LoginRequest;
import com.altev.ecommerce.dto.SignupRequest;
import com.altev.ecommerce.service.IAuthService;
import com.altev.ecommerce.service.impl.AuthService;
import com.altev.ecommerce.service.impl.JwtTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    

    private final AuthenticationManager authManager;
    private final JwtTokenService jwtTokenService;
    private final IAuthService authService;

    public AuthController(AuthenticationManager authManager, JwtTokenService jwtTokenService, AuthService authService) {
        this.authManager = authManager;
        this.jwtTokenService = jwtTokenService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwtToken = jwtTokenService.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(jwtToken));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {
            String jwtToken = authService.registerUser(request);
            return ResponseEntity.ok().body(new JwtResponse(jwtToken));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

package com.altev.ecommerce.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtTokenService {
    // Use a strong secret (at least 512 bits for HS512)
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.secret.validation.duration}")
    private Integer secretValidationDuration;
    private Algorithm jwtAlgorithm;
    private JWTVerifier jwtVerifier;
    
    @PostConstruct
    public void init() {
        // Initialize the signing algorithm and verifier once
        jwtAlgorithm = Algorithm.HMAC512(secret);
        jwtVerifier = JWT.require(jwtAlgorithm).withIssuer("altenApp").build();
    }

    public String generateToken(UserDetails userDetails) {
        Instant now = Instant.now();
        return JWT.create()
            .withSubject(userDetails.getUsername())
            .withIssuer("altenApp")
            .withIssuedAt(Date.from(now))
            .withExpiresAt(Date.from(now.plus(Duration.ofHours(secretValidationDuration))))
            .sign(jwtAlgorithm);
    }

    public String validateTokenAndGetUsername(String token) {
        try {
            DecodedJWT jwt = jwtVerifier.verify(token);
            return jwt.getSubject();  // if token is valid, return username (subject)
        } catch (JWTVerificationException ex) {
            // token is invalid or expired
            return null;
        }
    }
}

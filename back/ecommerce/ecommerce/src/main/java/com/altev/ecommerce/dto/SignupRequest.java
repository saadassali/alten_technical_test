package com.altev.ecommerce.dto;

public record SignupRequest(
        String email,
        String username,
        String firstname,
        String password,
        Boolean isAdmin
) {}


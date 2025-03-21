package com.altev.ecommerce.service;

import com.altev.ecommerce.dto.SignupRequest;

public interface IAuthService {
    String registerUser(SignupRequest request);
}

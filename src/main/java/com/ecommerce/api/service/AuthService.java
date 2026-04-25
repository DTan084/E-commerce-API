package com.ecommerce.api.service;

import com.ecommerce.api.dto.request.LoginDto;
import com.ecommerce.api.dto.request.SignUpDto;
import com.ecommerce.api.dto.response.JwtAuthResponse;

public interface AuthService {
    JwtAuthResponse login(LoginDto loginDto);
    String register(SignUpDto signUpDto);
}

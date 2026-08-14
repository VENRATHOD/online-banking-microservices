package com.banking.auth.service;

import com.banking.auth.dto.request.LoginRequest;
import com.banking.auth.dto.request.RegisterRequest;
import com.banking.auth.dto.response.JwtResponse;
import com.banking.auth.dto.response.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    //LoginResponse login(LoginRequest request);
    JwtResponse login(LoginRequest request);
}
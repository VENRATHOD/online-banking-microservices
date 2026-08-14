package com.banking.auth.service.Imp;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.banking.auth.dto.request.LoginRequest;
import com.banking.auth.dto.request.RegisterRequest;
import com.banking.auth.dto.response.JwtResponse;
import com.banking.auth.dto.response.RegisterResponse;
import com.banking.auth.entity.User;
import com.banking.auth.exception.UserAlreadyExistsException;
import com.banking.auth.repository.UserRepository;
import com.banking.auth.security.JwtUtil;
import com.banking.auth.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtUtil jwtUtil) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        return new RegisterResponse("User Registered Successfully");
    }

    @Override
    public JwtResponse login(LoginRequest request) {

        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Generate JWT Token
        String token = jwtUtil.generateToken(request.getEmail());

        // Return JWT Response
        return new JwtResponse(token);
    }
}
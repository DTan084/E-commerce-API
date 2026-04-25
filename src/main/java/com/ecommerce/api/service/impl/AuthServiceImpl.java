package com.ecommerce.api.service.impl;

import com.ecommerce.api.dto.request.LoginDto;
import com.ecommerce.api.dto.request.SignUpDto;
import com.ecommerce.api.dto.response.JwtAuthResponse;
import com.ecommerce.api.model.enums.Role;
import com.ecommerce.api.entity.User;
import com.ecommerce.api.exception.EcommerceApiException;
import com.ecommerce.api.repository.UserRepository;
import com.ecommerce.api.security.JwtTokenProvider;
import com.ecommerce.api.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager, 
                           UserRepository userRepository, 
                           PasswordEncoder passwordEncoder, 
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public JwtAuthResponse login(LoginDto loginDto) {
        log.info("Login attempt for email: {}", loginDto.getEmail());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return new JwtAuthResponse(token);
    }

    @Override
    public String register(SignUpDto signUpDto) {
        log.info("Registration attempt for email: {}", signUpDto.getEmail());
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new EcommerceApiException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        User user = User.builder()
                .name(signUpDto.getName())
                .email(signUpDto.getEmail())
                .passwordHash(passwordEncoder.encode(signUpDto.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }
}

package com.siva.expense_approval_system.application.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.siva.expense_approval_system.api.dto.request.LoginRequest;
import com.siva.expense_approval_system.api.dto.response.LoginResponse;
import com.siva.expense_approval_system.application.service.AuthService;
import com.siva.expense_approval_system.application.service.RefreshTokenService;
import com.siva.expense_approval_system.domain.repository.UserRepository;
import com.siva.expense_approval_system.infrastructure.security.JwtService;



@Service

public class AuthServiceImpl implements AuthService{
    
     
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    
    

     public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
            RefreshTokenService refreshTokenService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }



     @Override
    public LoginResponse login(LoginRequest request){
        Authentication authentication = authenticationManager.authenticate(
                         new UsernamePasswordAuthenticationToken(
                            request.getEmail(), 
                            request.getPassword()
            )
        );

        return new LoginResponse("", "", "Bearer");
    }

}

package com.siva.expense_approval_system.application.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.siva.expense_approval_system.api.dto.request.LoginRequest;
import com.siva.expense_approval_system.api.dto.response.LoginResponse;
import com.siva.expense_approval_system.api.dto.response.RefreshTokenRequest;
import com.siva.expense_approval_system.application.service.AuditService;
import com.siva.expense_approval_system.application.service.AuthService;
import com.siva.expense_approval_system.application.service.RefreshTokenService;
import com.siva.expense_approval_system.domain.enums.AuditActionType;
import com.siva.expense_approval_system.domain.enums.AuditEntityType;
import com.siva.expense_approval_system.domain.model.RefreshToken;
import com.siva.expense_approval_system.domain.model.User;
import com.siva.expense_approval_system.domain.repository.UserRepository;
import com.siva.expense_approval_system.infrastructure.security.JwtService;

import jakarta.transaction.Transactional;



@Service
public class AuthServiceImpl implements AuthService{
    
     
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuditService auditService;
    

     public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
            RefreshTokenService refreshTokenService, JwtService jwtService, PasswordEncoder passwordEncoder,AuditService auditService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.auditService = auditService;
    }



     @Override
    public LoginResponse login(LoginRequest request){
        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {
            if (!user.getPassword().startsWith("$2") && request.getPassword().equals(user.getPassword())) {
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                userRepository.save(user);
            }
        });

        Authentication authentication = authenticationManager.authenticate(
                         new UsernamePasswordAuthenticationToken(
                            request.getEmail(), 
                            request.getPassword()
            )
        );

        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new IllegalStateException("Authenticated user was not found"));

    String accessToken = jwtService.generateToken(user);

    String refreshToken =
            refreshTokenService.createRefreshToken(user);

    return new LoginResponse(
            accessToken,
            refreshToken,
            "Bearer"
    );
    }

@Override
@Transactional
public LoginResponse refreshToken(RefreshTokenRequest request) {

    RefreshToken refreshToken = refreshTokenService
            .findByToken(request.getRefreshToken())
            .orElseThrow(() ->
                    new RuntimeException("Invalid refresh token"));

    refreshToken = refreshTokenService.verifyExpiration(refreshToken);

    User user = refreshToken.getUser();

    String accessToken = jwtService.generateToken(user);

    refreshTokenService.revoke(refreshToken);

    String newRefreshToken =
            refreshTokenService.createRefreshToken(user);

    auditService.log(
            user.getTenant(),
            AuditActionType.LOGIN,
            AuditEntityType.USER,
            user.getId(),
            "Refresh token rotated"
    );

    return new LoginResponse(accessToken,newRefreshToken,"Bearer");
}

@Override
@Transactional
public void logout(RefreshTokenRequest request) {

    RefreshToken refreshToken = refreshTokenService
            .findByToken(request.getRefreshToken())
            .orElseThrow(() ->
                    new RuntimeException("Invalid refresh token"));

    refreshToken = refreshTokenService.verifyExpiration(refreshToken);

    refreshTokenService.revoke(refreshToken);

    User user = refreshToken.getUser();

    auditService.log(
            user.getTenant(),
            AuditActionType.LOGOUT,
            AuditEntityType.USER,
            user.getId(),
            "User logout"
    );
}

}

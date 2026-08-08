package com.siva.expense_approval_system.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.siva.expense_approval_system.api.dto.request.LoginRequest;
import com.siva.expense_approval_system.api.dto.response.LoginResponse;
import com.siva.expense_approval_system.api.dto.response.RefreshTokenRequest;
import com.siva.expense_approval_system.application.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

       
    @PostMapping("/refresh")
    @PreAuthorize("permitAll()")
    public ResponseEntity<LoginResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {

        return ResponseEntity.ok(authService.refreshToken(request));
}

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenRequest request) {

    authService.logout(request);

    return ResponseEntity.ok().build();
   }
}

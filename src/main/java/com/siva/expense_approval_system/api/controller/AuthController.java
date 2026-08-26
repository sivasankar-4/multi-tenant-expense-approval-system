package com.siva.expense_approval_system.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.siva.expense_approval_system.api.dto.request.ForgotPasswordRequest;
import com.siva.expense_approval_system.api.dto.request.LoginRequest;
import com.siva.expense_approval_system.api.dto.request.ResetPasswordRequest;
import com.siva.expense_approval_system.api.dto.request.SignUpRequest;
import com.siva.expense_approval_system.api.dto.request.VerifyOtpRequest;
import com.siva.expense_approval_system.api.dto.response.LoginResponse;
import com.siva.expense_approval_system.api.dto.response.RefreshTokenRequest;
import com.siva.expense_approval_system.api.dto.response.ResetTokenResponse;
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
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenRequest request) {

    authService.logout(request);

    return ResponseEntity.ok().build();
   }
   
   @PostMapping("/signup")
   @PreAuthorize("permitAll()")
   public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest request) {
    authService.signUp(request);
    return ResponseEntity.status(201).build();
   }
   
   @PostMapping("/forgot-password")
   @PreAuthorize("permitAll()")
   public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
       authService.forgotPassword(request.getEmail());
         return ResponseEntity.ok("If the account exists,an OTP has been sent.");
   }

       @PostMapping("/verify-otp")
       @PreAuthorize("permitAll()")
       public ResponseEntity<ResetTokenResponse> verifyOtp(
                       @Valid @RequestBody VerifyOtpRequest request) {

        return ResponseEntity.ok(
            authService.verifyOtp(request)
       );
    }

            @PostMapping("/reset-password")
            @PreAuthorize("permitAll()")
          public ResponseEntity<Void> resetPassword(
                              @Valid @RequestBody ResetPasswordRequest request) {

                authService.resetPassword(request);

                return ResponseEntity.ok().build();
    }
}

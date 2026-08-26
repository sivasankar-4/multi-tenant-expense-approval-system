package com.siva.expense_approval_system.application.service;

import com.siva.expense_approval_system.api.dto.request.LoginRequest;
import com.siva.expense_approval_system.api.dto.request.ResetPasswordRequest;
import com.siva.expense_approval_system.api.dto.request.SignUpRequest;
import com.siva.expense_approval_system.api.dto.request.VerifyOtpRequest;
import com.siva.expense_approval_system.api.dto.response.LoginResponse;
import com.siva.expense_approval_system.api.dto.response.RefreshTokenRequest;
import com.siva.expense_approval_system.api.dto.response.ResetTokenResponse;

public interface AuthService {
  
      LoginResponse login(LoginRequest request);
      
      LoginResponse refreshToken(RefreshTokenRequest request);

      void logout(RefreshTokenRequest request);

      void signUp(SignUpRequest request); 

      void forgotPassword(String email);

      ResetTokenResponse verifyOtp(VerifyOtpRequest request);

      void resetPassword(ResetPasswordRequest request);
} 

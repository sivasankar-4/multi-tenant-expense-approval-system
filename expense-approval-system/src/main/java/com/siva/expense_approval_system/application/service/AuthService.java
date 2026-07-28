package com.siva.expense_approval_system.application.service;

import com.siva.expense_approval_system.api.dto.request.LoginRequest;
import com.siva.expense_approval_system.api.dto.response.LoginResponse;

public interface AuthService {
  
      LoginResponse login(LoginRequest request);
    
} 

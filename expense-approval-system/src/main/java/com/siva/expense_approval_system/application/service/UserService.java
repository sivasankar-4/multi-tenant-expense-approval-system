package com.siva.expense_approval_system.application.service;

import java.util.List;

import com.siva.expense_approval_system.domain.model.User;

public interface UserService {
   
    User createUser(User user);

    User getUserById(Long id);

    List<User> getAllUsers();

    User updateUser(Long id , User user);

    void deleteUser(Long id);
    
} 

package com.siva.expense_approval_system.application.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.siva.expense_approval_system.application.service.UserService;
import com.siva.expense_approval_system.domain.model.User;
import com.siva.expense_approval_system.domain.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
    

    private final UserRepository userRepository;
    
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(@NonNull User user) {
        return userRepository.save(Objects.requireNonNull(user, "User must not be null"));
    }

    @Override
    public User getUserById(@NonNull Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
    }
    
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User updateUser(@NonNull Long id, @NonNull User user) {
        User existingUser = getUserById(id);
        existingUser.setTenant(user.getTenant());
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setRole(user.getRole());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(@NonNull Long id){
        User user = getUserById(id);

        userRepository.delete(user);
    }


}

package com.siva.expense_approval_system.application.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.siva.expense_approval_system.application.service.AuditService;
import com.siva.expense_approval_system.application.service.UserService;
import com.siva.expense_approval_system.domain.enums.AuditActionType;
import com.siva.expense_approval_system.domain.enums.AuditEntityType;
import com.siva.expense_approval_system.domain.model.User;
import com.siva.expense_approval_system.domain.repository.UserRepository;
import com.siva.expense_approval_system.infrastructure.security.CurrentUserService;

@Service
public class UserServiceImpl implements UserService{
    

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUserService currentUserService;
    private final AuditService auditService;
    
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
            CurrentUserService currentUserService,AuditService auditService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.currentUserService = currentUserService;
        this.auditService = auditService;
    }

    @Override
    public User createUser(@NonNull User user) {
        user.setTenant(currentUserService.getCurrentTenant());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(Objects.requireNonNull(user, "User must not be null"));

        auditService.log(
    user.getTenant(),
    AuditActionType.CREATE,
    AuditEntityType.USER,
    savedUser.getId(),
    "Created employee"
);

      return savedUser;
    }

    @Override
    public User getUserById(@NonNull Long id) {
        return userRepository.findByIdAndTenantId(id, getCurrentTenantId())
                .orElseThrow(() -> new AccessDeniedException("User not found or does not belong to the current tenant."));
    }
    
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAllByTenantId(getCurrentTenantId());
    }

    @Override
    @Transactional
    public User updateUser(@NonNull Long id, @NonNull User user) {
        User existingUser = getUserById(id);
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

    private Long getCurrentTenantId() {
        if (currentUserService.getCurrentTenant() == null || currentUserService.getCurrentTenant().getId() == null) {
            throw new AccessDeniedException("Current user is not associated with a tenant.");
        }
        return currentUserService.getCurrentTenant().getId();
    }


}

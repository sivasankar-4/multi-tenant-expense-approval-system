package com.siva.expense_approval_system.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.siva.expense_approval_system.domain.model.User;

public interface UserRepository extends JpaRepository<User,Long>{

    Optional<User> findByIdAndTenantId(Long id, Long tenantId);

    List<User> findAllByTenantId(Long tenantId);

    Optional<User> findByEmail(String email);
}

package com.siva.expense_approval_system.domain.repository;

import com.siva.expense_approval_system.domain.model.RefreshToken;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByTokenHash(String tokenHash);
    Optional<RefreshToken> findByUser(User user);

    void deletedByUser(User user);
}


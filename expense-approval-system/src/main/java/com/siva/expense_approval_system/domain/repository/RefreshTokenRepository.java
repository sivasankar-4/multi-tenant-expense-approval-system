package com.siva.expense_approval_system.domain.repository;

import com.siva.expense_approval_system.domain.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}


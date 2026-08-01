package com.siva.expense_approval_system.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.siva.expense_approval_system.domain.model.Expense;


public interface ExpenseRepository extends JpaRepository<Expense,Long> {

    Optional<Expense> findByIdAndTenantId(Long id, Long tenantId);

    List<Expense> findAllByTenantId(Long tenantId);

    List<Expense> findAllByTenantIdAndSubmittedById(Long tenantId, Long submittedById);
}

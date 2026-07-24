package com.siva.expense_approval_system.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.siva.expense_approval_system.domain.model.ApprovalChain;
import com.siva.expense_approval_system.domain.model.Tenant;

public interface ApprovalChainRepository extends JpaRepository <ApprovalChain,Long>{


    // it is used to find the approval chains with the amount ranges 
    List<ApprovalChain> findByTenantAndMinAmountLessThanEqualAndMaxAmountGreaterThanEqualOrderByStepOrderAsc(
        Tenant tenant,
        BigDecimal amount1,
        BigDecimal amount2
);

    List<ApprovalChain> findByTenantOrderByStepOrderAsc(Tenant tenant);
    
}

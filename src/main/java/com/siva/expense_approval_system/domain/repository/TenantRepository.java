package com.siva.expense_approval_system.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.siva.expense_approval_system.domain.model.Tenant;

public interface TenantRepository extends JpaRepository<Tenant,Long>{
    
}

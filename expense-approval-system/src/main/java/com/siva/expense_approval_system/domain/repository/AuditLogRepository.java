package com.siva.expense_approval_system.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.siva.expense_approval_system.domain.model.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog,Long>{

}

package com.siva.expense_approval_system.application.impl;

import org.springframework.stereotype.Service;

import com.siva.expense_approval_system.application.service.TenantService;
import com.siva.expense_approval_system.domain.repository.TenantRepository;

@Service
public class TenantServiceImpl implements TenantService{
    
     
    private final TenantRepository tenantRepository;

    public TenantServiceImpl(TenantRepository tenantRepository){
        this.tenantRepository = tenantRepository;
    }
    

}

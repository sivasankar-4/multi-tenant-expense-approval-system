package com.siva.expense_approval_system.application.service;

import java.util.List;

import com.siva.expense_approval_system.domain.model.Tenant;

public interface TenantService {


   Tenant createTenant(Tenant tenant);

   Tenant getTenantById(Long Id);

   List<Tenant> getAllTenants();

   Tenant updateTenant(Long id,Tenant tenant);

   void deleteTenant(Long id);

   

}

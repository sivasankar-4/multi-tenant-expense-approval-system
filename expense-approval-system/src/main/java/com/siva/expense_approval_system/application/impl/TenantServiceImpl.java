package com.siva.expense_approval_system.application.impl;

import java.util.List;
//import java.util.Objects;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.siva.expense_approval_system.application.service.TenantService;
import com.siva.expense_approval_system.domain.model.Tenant;
import com.siva.expense_approval_system.domain.repository.TenantRepository;

@Service
public class TenantServiceImpl implements TenantService{
    
   
    private final TenantRepository tenantRepository;

    public TenantServiceImpl(TenantRepository tenantRepository){

        this.tenantRepository = tenantRepository;
    }
    
    @Override
    public Tenant createTenant(@NonNull Tenant tenant){
        return tenantRepository.save(tenant);

    }
    
    @Override
    public Tenant getTenantById(@NonNull Long id){
      
        return tenantRepository.findById(id)
                         .orElseThrow(() -> new IllegalArgumentException("Tenant Not Found" + id));
                
    }
   
     @Override
    public List<Tenant> getAllTenants() {

        return tenantRepository.findAll();
    }

    @Override
    public Tenant updateTenant(@NonNull Long id, @NonNull Tenant tenant){
        
         Tenant existingTenant = getTenantById(id);
         existingTenant.setName(tenant.getName());
         return tenantRepository.save(existingTenant);
    }


    @Override
    public void deleteTenant(@NonNull Long id){

        Tenant tenant = getTenantById(id);

        tenantRepository.delete(tenant);
    }

}

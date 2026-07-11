package com.siva.expense_approval_system.infrastructure.config;


import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.siva.expense_approval_system.domain.model.Tenant;
import com.siva.expense_approval_system.domain.repository.ExpenseRepository;
import com.siva.expense_approval_system.domain.repository.TenantRepository;
import com.siva.expense_approval_system.domain.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner{

    //private final UserRepository userRepository;
     private final TenantRepository tenantRepository;
    // private final ExpenseRepository expenseRepository;

    
    
     public DataLoader(UserRepository userRepository, TenantRepository tenantRepository,
            ExpenseRepository expenseRepository) {
        // this.userRepository = userRepository;
         this.tenantRepository = tenantRepository;
        // this.expenseRepository = expenseRepository;
    }
     public void run(String... args) throws Exception{


        Tenant tenant = new Tenant();

        tenant.setName("TechCorp");
        tenant.setcreated_At(LocalDateTime.now());
        tenantRepository.save(tenant);
        System.out.println("Application start successfully");
     }
}

package com.siva.expense_approval_system.infrastructure.security;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.siva.expense_approval_system.domain.model.Tenant;
import com.siva.expense_approval_system.domain.model.User;

@Service
public class CurrentUserServiceImpl implements CurrentUserService{
    

    @Override
    public User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

         System.out.println(authentication);
        System.out.println(authentication.getPrincipal());

        CustomUserDetails currentuser = (CustomUserDetails) authentication.getPrincipal();
         
        return currentuser.getuser();
    }

    @Override
    public Tenant getCurrentTenant() {
        return getCurrentUser().getTenant();
    }
}

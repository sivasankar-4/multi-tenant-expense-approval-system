

import org.springframework.security.core.userdetails.User;

import com.siva.expense_approval_system.infrastructure.security;

public interface CurrentUserService {
    

    User getCurrentUser();
    Tenant getCurrentTenant();
}

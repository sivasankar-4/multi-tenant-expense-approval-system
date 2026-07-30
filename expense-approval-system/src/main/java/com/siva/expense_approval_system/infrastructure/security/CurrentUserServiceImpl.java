import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserServiceImpl implements CurrentUserService{
    

    @Override
    public User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails currentuser = (CustomUserDetails) authentication.getPrincipal();

        return currentUser.getUser();
    }

    @Override
    public Tenant getCurrentTenant() {
        return getCurrentUser().getTenant();
    }
}

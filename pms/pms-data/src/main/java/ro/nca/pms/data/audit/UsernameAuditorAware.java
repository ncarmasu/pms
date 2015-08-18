package ro.nca.pms.data.audit;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UsernameAuditorAware implements AuditorAware<String> {
    
    @Override
    public String getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
 
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
 
        return ((User) authentication.getPrincipal()).getName();
    }
}
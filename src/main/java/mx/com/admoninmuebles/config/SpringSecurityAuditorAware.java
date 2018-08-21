package mx.com.admoninmuebles.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import mx.com.admoninmuebles.security.CustomUserDetails;

public class SpringSecurityAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        Optional<Long> optUserId = Optional.empty();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof CustomUserDetails) {
            optUserId = Optional.of(((CustomUserDetails) authentication.getPrincipal()).getId());
        }

        return optUserId;
    }
}

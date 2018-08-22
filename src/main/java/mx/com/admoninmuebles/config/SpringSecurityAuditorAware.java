package mx.com.admoninmuebles.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import mx.com.admoninmuebles.persistence.model.Usuario;
import mx.com.admoninmuebles.security.CustomUserDetails;

public class SpringSecurityAuditorAware implements AuditorAware<Usuario> {

    @Override
    public Optional<Usuario> getCurrentAuditor() {
        Optional<Usuario> optUsuario = Optional.empty();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof CustomUserDetails) {
            optUsuario = Optional.of(((CustomUserDetails) authentication.getPrincipal()).getUsuario());
        }

        return optUsuario;
    }
}

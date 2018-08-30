package mx.com.admoninmuebles.security;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


public final class SecurityUtils {
	
	public static final String ROLE_ANONIMO = "ROLE_ANONYMOUS";
	
	 private SecurityUtils() {
	    }

	    public static Optional<String> getCurrentUserLogin() {
	        SecurityContext securityContext = SecurityContextHolder.getContext();
	        return Optional.ofNullable(securityContext.getAuthentication())
	            .map(authentication -> {
	                if (authentication.getPrincipal() instanceof UserDetails) {
	                    UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
	                    return springSecurityUser.getUsername();
	                } else if (authentication.getPrincipal() instanceof String) {
	                    return (String) authentication.getPrincipal();
	                }
	                return null;
	            });
	    }

	    public static Optional<String> getCurrentUserJWT() {
	        SecurityContext securityContext = SecurityContextHolder.getContext();
	        return Optional.ofNullable(securityContext.getAuthentication())
	            .filter(authentication -> authentication.getCredentials() instanceof String)
	            .map(authentication -> (String) authentication.getCredentials());
	    }

	    public static boolean isAuthenticated() {
	        SecurityContext securityContext = SecurityContextHolder.getContext();
	        return Optional.ofNullable(securityContext.getAuthentication())
	            .map(authentication -> authentication.getAuthorities().stream()
	                .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(ROLE_ANONIMO)))
	            .orElse(false);
	    }

	    public static boolean isCurrentUserInRole(String authority) {
	        SecurityContext securityContext = SecurityContextHolder.getContext();
	        return Optional.ofNullable(securityContext.getAuthentication())
	            .map(authentication -> authentication.getAuthorities().stream()
	                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority)))
	            .orElse(false);
	    }

}


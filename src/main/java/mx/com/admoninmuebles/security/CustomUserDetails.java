package mx.com.admoninmuebles.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import mx.com.admoninmuebles.persistence.model.Rol;
import mx.com.admoninmuebles.persistence.model.Usuario;

public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;
    private final Usuario usuario;

    public CustomUserDetails(final Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Rol rol : usuario.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(rol.getNombre()));
            rol.getPrivilegios().stream().map(p -> new SimpleGrantedAuthority(p.getNombre())).forEach(authorities::add);
        }
        return authorities;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !usuario.isCuentaExpirada();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !usuario.isCuentaBloqueada();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !usuario.isCredencialesExpiradas();
    }

    @Override
    public boolean isEnabled() {
        return usuario.isActivo();
    }

    @Override
    public String getUsername() {
        return usuario.getUsername();
    }

    @Override
    public String getPassword() {
        return usuario.getContrasenia();
    }

}

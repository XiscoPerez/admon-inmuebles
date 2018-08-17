package mx.com.admoninmuebles.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mx.com.admoninmuebles.persistence.model.Usuario;
import mx.com.admoninmuebles.persistence.repository.UsuarioRepository;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) {
        Optional<Usuario> optUsuario = userRepository.findByUsername(username);
        if (optUsuario.isPresent()) {
            Usuario usuario = optUsuario.get();
            return new CustomUserDetails(usuario);
        } else {
            throw new UsernameNotFoundException("No user present with username: " + username);
        }
    }

}

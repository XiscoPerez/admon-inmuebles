package mx.com.admoninmuebles.dataloader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import mx.com.admoninmuebles.persistence.model.Privilegio;
import mx.com.admoninmuebles.persistence.model.Rol;
import mx.com.admoninmuebles.persistence.model.Usuario;
import mx.com.admoninmuebles.persistence.repository.PrivilegioRepository;
import mx.com.admoninmuebles.persistence.repository.RolRepository;
import mx.com.admoninmuebles.persistence.repository.UsuarioRepository;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PrivilegioRepository privilegioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        // == create initial privilegios
        final Privilegio readPrivilegio = createPrivilegioIfNotFound("READ_PRIVILEGE");
        final Privilegio writePrivilegio = createPrivilegioIfNotFound("WRITE_PRIVILEGE");
        final Privilegio passwordPrivilegio = createPrivilegioIfNotFound("CHANGE_PASSWORD_PRIVILEGE");

        // == create initial rols
        final List<Privilegio> adminPrivilegios = new ArrayList<>(Arrays.asList(readPrivilegio, writePrivilegio, passwordPrivilegio));
        final List<Privilegio> usuarioPrivilegios = new ArrayList<>(Arrays.asList(readPrivilegio, passwordPrivilegio));
        final Rol adminRol = createRolIfNotFound("ROLE_ADMIN", adminPrivilegios);
        final Rol userRol = createRolIfNotFound("ROLE_USER", usuarioPrivilegios);

        // == create initial usuario
        createUsuarioIfNotFound("admin", "Administrador", "", "", "admin", new ArrayList<>(Arrays.asList(adminRol)));
        createUsuarioIfNotFound("user", "User", "", "", "user", new ArrayList<>(Arrays.asList(userRol)));

        alreadySetup = true;
    }

    @Transactional
    public final Privilegio createPrivilegioIfNotFound(final String nombre) {
        Optional<Privilegio> optPrivilegio = privilegioRepository.findByNombre(nombre);
        Privilegio privilegio = optPrivilegio.orElse(new Privilegio());
        if (!optPrivilegio.isPresent()) {
            privilegio.setNombre(nombre);
            privilegio = privilegioRepository.save(privilegio);
        }
        return privilegio;
    }

    @Transactional
    public final Rol createRolIfNotFound(final String nombre, final Collection<Privilegio> privilegios) {
        Optional<Rol> optRol = rolRepository.findByNombre(nombre);
        Rol rol = optRol.orElse(new Rol());
        if (!optRol.isPresent()) {
            rol.setNombre(nombre);
            rol.setPrivilegios(privilegios);
            rol = rolRepository.save(rol);
        }

        return rol;
    }

    @Transactional
    public final Usuario createUsuarioIfNotFound(final String username, final String firstNombre, final String apellidoPatarno, final String apellidoMaterno, final String contrasenia,
            final Collection<Rol> roles) {
        Optional<Usuario> optUsuario = usuarioRepository.findByUsername(username);
        Usuario usuario = optUsuario.orElse(new Usuario());
        if (!optUsuario.isPresent()) {
            usuario.setUsername(username);
            usuario.setNombre(firstNombre);
            usuario.setApellidoPatarno(apellidoPatarno);
            usuario.setApellidoMaterno(apellidoMaterno);

            usuario.setContrasenia(passwordEncoder.encode(contrasenia));
            usuario.setRoles(roles);
            usuario = usuarioRepository.save(usuario);
        }
        return usuario;
    }

}
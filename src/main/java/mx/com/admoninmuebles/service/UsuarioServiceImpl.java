package mx.com.admoninmuebles.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import mx.com.admoninmuebles.dto.UsuarioDto;
import mx.com.admoninmuebles.persistence.model.Rol;
import mx.com.admoninmuebles.persistence.model.Usuario;
import mx.com.admoninmuebles.persistence.repository.RolRepository;
import mx.com.admoninmuebles.persistence.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository userRepository;
    @Autowired
    private RolRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Usuario save(final UsuarioDto userDto) {
        Usuario usuario = modelMapper.map(userDto, Usuario.class);
        usuario.setContrasenia(passwordEncoder.encode(usuario.getContrasenia()));
        Optional<Rol> optRol = roleRepository.findByNombre("ROLE_USER");
        if (optRol.isPresent()) {
            Collection<Rol> roles = new ArrayList<>();
            roles.add(optRol.get());
            usuario.setRoles(roles);
        }
        return userRepository.save(usuario);
    }

}

package mx.com.admoninmuebles.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import mx.com.admoninmuebles.dto.CambioContraseniaDto;
import mx.com.admoninmuebles.dto.UsuarioDto;
import mx.com.admoninmuebles.error.BusinessException;
import mx.com.admoninmuebles.persistence.model.Rol;
import mx.com.admoninmuebles.persistence.model.Usuario;
import mx.com.admoninmuebles.persistence.repository.RolRepository;
import mx.com.admoninmuebles.persistence.repository.UsuarioRepository;
import mx.com.admoninmuebles.security.SecurityUtils;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UsuarioDto crearCuenta(final UsuarioDto userDto) {
    	Optional<Usuario> usuarioOptional = userRepository.findByUsername(userDto.getUsername());
        if (usuarioOptional.isPresent()) {
            throw new BusinessException("usuario.error.yaexiste");
        }
        String contrasenia = RandomStringUtils.randomAlphanumeric(8);
        userDto.setContrasenia(contrasenia);
        Usuario usuario = modelMapper.map(userDto, Usuario.class);

        Collection<Rol> roles = new ArrayList<>();
        roles.add(rolRepository.findById(userDto.getRolSeleccionado()).get());
        usuario.setRoles(roles);
        Usuario usuarioCreado = userRepository.save(usuario);
        return modelMapper.map(usuarioCreado, UsuarioDto.class);
    }
    
    @Override
    public UsuarioDto editarCuenta(final UsuarioDto userDto) {
        Usuario usuario = modelMapper.map(userDto, Usuario.class);

        Collection<Rol> roles = new ArrayList<>();
        roles.add(rolRepository.findById(userDto.getRolSeleccionado()).get());
        usuario.setRoles(roles);
        Usuario usuarioCreado = userRepository.save(usuario);
        return modelMapper.map(usuarioCreado, UsuarioDto.class);
    }


    @Override
    public Collection<UsuarioDto> findAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false).map(usuario -> modelMapper.map(usuario, UsuarioDto.class)).collect(Collectors.toList());
    }

    @Override
    public UsuarioDto editarPerfil(final UsuarioDto userDto) {
        String usuarioAutenticado = SecurityUtils.getCurrentUserLogin().get();
        Optional<Usuario> usuarioOptional = userRepository.findByUsername(usuarioAutenticado);

        Usuario usuario = usuarioOptional.get();

        usuario.setApellidoMaterno(userDto.getApellidoMaterno());
        usuario.setApellidoPaterno(userDto.getApellidoPaterno());
        usuario.setNombre(userDto.getNombre());
        usuario.setCorreo(userDto.getCorreo());
        usuario.setFacebook(userDto.getFacebook());
        usuario.setFotoUrl(userDto.getFotoUrl());
        usuario.setGoogleMapsDir(userDto.getGoogleMapsDir());
        usuario.setTwiter(userDto.getTwiter());
        usuario.setYoutube(userDto.getYoutube());

        Usuario usuarioCreado = userRepository.save(usuario);
        return modelMapper.map(usuarioCreado, UsuarioDto.class);
    }

    @Override
    public UsuarioDto findById(final Long idUsuario) {
        Optional<Usuario> usuarioOptional = userRepository.findById(idUsuario);

        if (!usuarioOptional.isPresent()) {

        }

        return modelMapper.map(usuarioOptional.get(), UsuarioDto.class);
    }

    @Override
    public UsuarioDto findUserLogin() {
        String usuarioAutenticado = SecurityUtils.getCurrentUserLogin().get();
        Optional<Usuario> usuarioOptional = userRepository.findByUsername(usuarioAutenticado);

        return modelMapper.map(usuarioOptional.get(), UsuarioDto.class);
    }

    @Override
    public void deleteById(final Long idUsuario) {
        Optional<Usuario> usuarioOptional = userRepository.findById(idUsuario);

        if (!usuarioOptional.isPresent()) {
        	 throw new BusinessException("usuario.error.noencontrado");
        }

        userRepository.deleteById(idUsuario);

    }

    @Override
    public UsuarioDto cambiarContrasenia(final CambioContraseniaDto cambioContraseniaDto) {
        String usuarioAutenticado = SecurityUtils.getCurrentUserLogin().get();
        Optional<Usuario> usuarioOptional = userRepository.findByUsername(usuarioAutenticado);

        if (!usuarioOptional.isPresent()) {
            throw new BusinessException("usuario.error.noencontrado");
        }
        Usuario usuarioLogin = usuarioOptional.get();

        if (!passwordEncoder.matches(cambioContraseniaDto.getContraseniaAnterior(), usuarioLogin.getContrasenia())) {
            throw new BusinessException("usuario.error.contraseniaanterior.novalida");
        }

        if (!cambioContraseniaDto.getContraseniaNueva().equals(cambioContraseniaDto.getContraseniaConfirmacion())) {
            throw new BusinessException("usuario.error.contrasenias.noiguales");
        }

        usuarioLogin.setContrasenia(passwordEncoder.encode(cambioContraseniaDto.getContraseniaNueva()));

        userRepository.save(usuarioLogin);

        return modelMapper.map(usuarioLogin, UsuarioDto.class);

    }

    @Override
    public UsuarioDto findByUsernameOrCorreo(final String usernameCorreo) {

        Optional<Usuario> usuarioOptional;

        if (new EmailValidator().isValid(usernameCorreo, null)) {
            usuarioOptional = userRepository.findByCorreo(usernameCorreo);
        } else {
            usuarioOptional = userRepository.findByUsername(usernameCorreo);
        }

        if (!usuarioOptional.isPresent()) {
            throw new UsernameNotFoundException("No se econtro el usuario");
        }

        return modelMapper.map(usuarioOptional.get(), UsuarioDto.class);
    }

    @Override
    public Collection<UsuarioDto> findByRolesNombre(final String nombre) {
        return StreamSupport.stream(userRepository.findByRolesNombre(nombre).spliterator(), false).map(usuario -> modelMapper.map(usuario, UsuarioDto.class)).collect(Collectors.toList());

    }

    @Override
    public Collection<UsuarioDto> findByRolesNombreAndAreasServicioId(final String nombre, final Long id) {
        return StreamSupport.stream(userRepository.findByRolesNombreAndAreasServicioId(nombre, id).spliterator(), false).map(usuario -> modelMapper.map(usuario, UsuarioDto.class))
                .collect(Collectors.toList());
    }

}

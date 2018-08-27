package mx.com.admoninmuebles.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import mx.com.admoninmuebles.dto.RolDto;
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
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private CorreoService correoService;
    
    @Autowired
    private RolRepository rolRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public UsuarioDto crearCuenta(final UsuarioDto userDto) {
    	String contrasenia = RandomStringUtils.randomAlphanumeric(8);
    	System.out.println("CONTRASENIA: " + contrasenia);
    	userDto.setContrasenia(contrasenia);
    	Usuario usuario = modelMapper.map(userDto, Usuario.class);
    	
    	Collection<Rol> roles = new ArrayList<>();
    	for(Long idRol: userDto.getRolesSeleccionados()) {
    		roles.add(rolRepository.findById(idRol).get());
    	}
    	usuario.setRoles(roles);
    	Usuario usuarioCreado = userRepository.save(usuario);
//    	correoService.sendMail()
        return modelMapper.map(usuarioCreado, UsuarioDto.class);
    }

    @Override
    public Usuario save(final UsuarioDto userDto) {
        Usuario usuario = modelMapper.map(userDto, Usuario.class);
        usuario.setContrasenia(passwordEncoder.encode(usuario.getContrasenia()));
        return userRepository.save(usuario);
    }

	@Override
	public Collection<UsuarioDto> findAll() {
		return StreamSupport.stream(userRepository.findAll().spliterator(), false)
				.map(usuario -> modelMapper.map(usuario, UsuarioDto.class))
				.collect(Collectors.toList());
	}
	
	public static void main(String[] args) {
		String generatedString = RandomStringUtils.randomAlphanumeric(10);
	    System.out.println(generatedString);
	    
	    String generatedString2 = RandomStringUtils.random(10, true, true);
	    System.out.println(generatedString2);
	}

	@Override
	public UsuarioDto update(UsuarioDto userDto) {
    	Optional<Usuario> usuarioOptional = userRepository.findById(userDto.getIdUsuario());
    	
    	if(!usuarioOptional.isPresent()) {
    		
    	}
    	
    	Usuario usuario = usuarioOptional.get();
    	
    	usuario.setActivo(userDto.isActivo());
    	usuario.setApellidoMaterno(userDto.getApellidoMaterno());
    	usuario.setApellidoPaterno(userDto.getApellidoPaterno());
    	usuario.setNombre(userDto.getNombre());
    	
    	Collection<Rol> roles = new ArrayList<>();
    	for(Long idRol: userDto.getRolesSeleccionados()) {
    		roles.add(rolRepository.findById(idRol).get());
    	}
    	usuario.setRoles(roles);
    	Usuario usuarioCreado = userRepository.save(usuario);
        return modelMapper.map(usuarioCreado, UsuarioDto.class);
    }

	@Override
	public UsuarioDto findById(Long idUsuario) {
		Optional<Usuario> usuarioOptional = userRepository.findById(idUsuario);
    	
    	if(!usuarioOptional.isPresent()) {
    		
    	}
    	
    	return modelMapper.map(usuarioOptional.get(), UsuarioDto.class);
	}

	@Override
	public void deleteById(Long idUsuario) {
		Optional<Usuario> usuarioOptional = userRepository.findById(idUsuario);
    	
    	if(!usuarioOptional.isPresent()) {
    		
    	}
    	
    	userRepository.deleteById(idUsuario);
		
	}

}

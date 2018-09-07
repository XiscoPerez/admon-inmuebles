package mx.com.admoninmuebles.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import mx.com.admoninmuebles.dto.UsuarioDto;
import mx.com.admoninmuebles.dto.ActivacionUsuarioDto;
import mx.com.admoninmuebles.persistence.model.Usuario;
import mx.com.admoninmuebles.persistence.model.ActivacionUsuarioToken;
import mx.com.admoninmuebles.persistence.repository.UsuarioRepository;
import mx.com.admoninmuebles.persistence.repository.ActivacionUsuarioTokenRepository;

@Service
public class ActivacionUsuarioServiceImpl implements ActivacionUsuarioService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ActivacionUsuarioTokenRepository activacionUsuarioTokenRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UsuarioDto activar(ActivacionUsuarioDto verificacionContraseniaDto) {
		System.out.println("TOKEN: " + verificacionContraseniaDto.getToken());
		Optional<ActivacionUsuarioToken> verificacionTokenOpt = activacionUsuarioTokenRepository.findByToken(verificacionContraseniaDto.getToken());
		ActivacionUsuarioToken verificacionToken = verificacionTokenOpt.get();
		
		Usuario usuario = verificacionToken.getUsuario();
		
		usuario.setContrasenia(passwordEncoder.encode(verificacionContraseniaDto.getContrasenia()));
		usuario.setActivo(true);
		
		Usuario usuarioModificado = usuarioRepository.save(usuario);
		
//		verificacionToken.setUtilizado(true);
//		activacionUsuarioTokenRepository.save(verificacionToken);
		
		activacionUsuarioTokenRepository.delete(verificacionToken);
		
		return modelMapper.map(usuarioModificado, UsuarioDto.class);
	}
	
	
    @Override
    public void guardarToken(final UsuarioDto usuarioDto, final String token) {
    	Usuario usuario = usuarioRepository.findById(usuarioDto.getId()).get();
        final ActivacionUsuarioToken verificacionToken = new ActivacionUsuarioToken(token, usuario);
        activacionUsuarioTokenRepository.save(verificacionToken);
    }
    
    @Override
    public boolean isTokenValido(final String token) {
    	Optional<ActivacionUsuarioToken> verificacionTokenOpt = activacionUsuarioTokenRepository.findByToken(token);
    	if(!verificacionTokenOpt.isPresent()) {
    		return false;
    	}
    	
    	return !verificacionTokenOpt.get().isUtilizado();
    }
	
	

}

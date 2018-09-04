package mx.com.admoninmuebles.service;

import java.util.Collection;

import mx.com.admoninmuebles.dto.CambioContraseniaDto;
import mx.com.admoninmuebles.dto.UsuarioDto;
import mx.com.admoninmuebles.persistence.model.Usuario;

public interface UsuarioService {

    Usuario save(UsuarioDto userDto);
    
    UsuarioDto editarPerfil(UsuarioDto userDto);
    
    UsuarioDto findById(Long idUsuario);
    
    UsuarioDto findByUsernameOrCorreo(final String usernameCorreo);
    
    void deleteById(Long idUsuario);
    
    Collection<UsuarioDto> findAll();
    
    UsuarioDto crearCuenta(UsuarioDto userDto);
    
    UsuarioDto findUserLogin();
    
    UsuarioDto cambiarContrasenia(CambioContraseniaDto cambioContraseniaDto);

}

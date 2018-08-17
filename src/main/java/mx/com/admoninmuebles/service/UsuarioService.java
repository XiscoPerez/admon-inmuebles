package mx.com.admoninmuebles.service;

import mx.com.admoninmuebles.dto.UsuarioDto;
import mx.com.admoninmuebles.persistence.model.Usuario;

public interface UsuarioService {

    Usuario save(UsuarioDto userDto);
}

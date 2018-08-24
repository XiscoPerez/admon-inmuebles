package mx.com.admoninmuebles.service;

import java.util.Collection;

import mx.com.admoninmuebles.dto.RolDto;
import mx.com.admoninmuebles.persistence.model.Rol;
import mx.com.admoninmuebles.persistence.model.Usuario;

public interface RolService {
    Rol save(RolDto rolDto);

    Collection<Usuario> findUsuariosByNombreRol(String nombre);
}

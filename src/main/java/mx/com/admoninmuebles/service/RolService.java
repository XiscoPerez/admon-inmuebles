package mx.com.admoninmuebles.service;

import java.util.Collection;

import mx.com.admoninmuebles.dto.RolDto;
import mx.com.admoninmuebles.persistence.model.Rol;

public interface RolService {
    Rol save(RolDto rolDto);

    Collection<RolDto> findAll();
    
    public Collection<RolDto> getRolesSociosRepresentantes();

}

package mx.com.admoninmuebles.service;

import mx.com.admoninmuebles.dto.RolDto;
import mx.com.admoninmuebles.persistence.model.Rol;

public interface RolService {
    Rol save(RolDto rolDto);
}

package mx.com.admoninmuebles.service;

import mx.com.admoninmuebles.dto.SucursalDto;
import mx.com.admoninmuebles.persistence.model.Sucursal;

public interface SucursalService {
    Sucursal save(SucursalDto sucursalDto);
}

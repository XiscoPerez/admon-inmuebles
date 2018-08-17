package mx.com.admoninmuebles.service;

import mx.com.admoninmuebles.dto.BienInmuebleDto;
import mx.com.admoninmuebles.persistence.model.BienInmueble;

public interface BienInmuebleService {
    BienInmueble save(BienInmuebleDto bienInmuebleDto);
}

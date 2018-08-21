package mx.com.admoninmuebles.service;

import mx.com.admoninmuebles.dto.ZonaDto;
import mx.com.admoninmuebles.persistence.model.Zona;

public interface ZonaService {
    Zona save(ZonaDto zonaDto);
}

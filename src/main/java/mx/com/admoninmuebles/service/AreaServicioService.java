package mx.com.admoninmuebles.service;

import mx.com.admoninmuebles.dto.AreaServicioDto;
import mx.com.admoninmuebles.persistence.model.AreaServicio;

public interface AreaServicioService {
    AreaServicio save(AreaServicioDto areaServicioDto);
}

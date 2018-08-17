package mx.com.admoninmuebles.service;

import mx.com.admoninmuebles.dto.ServicioDto;
import mx.com.admoninmuebles.persistence.model.Servicio;

public interface ServicioService {
    Servicio save(ServicioDto servicioDto);
}

package mx.com.admoninmuebles.service;

import java.util.Collection;

import mx.com.admoninmuebles.dto.ServicioDto;
import mx.com.admoninmuebles.persistence.model.Servicio;

public interface ServicioService {
    Servicio save(ServicioDto servicioDto);
    Collection<ServicioDto> findAll();
    ServicioDto findById(Long idServicio);
    void deleteById(Long idServicio);
}

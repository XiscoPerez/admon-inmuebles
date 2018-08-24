package mx.com.admoninmuebles.service;

import java.util.Collection;

import mx.com.admoninmuebles.dto.ZonaDto;
import mx.com.admoninmuebles.persistence.model.Zona;

public interface ZonaService {
    Zona save(ZonaDto zonaDto);

    Collection<ZonaDto> findAll();

    ZonaDto findById(Long idZona);

    void deleteById(Long idZona);
}

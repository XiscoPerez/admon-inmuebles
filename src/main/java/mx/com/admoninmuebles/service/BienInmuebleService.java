package mx.com.admoninmuebles.service;

import java.util.Collection;

import mx.com.admoninmuebles.dto.InmuebleDto;
import mx.com.admoninmuebles.persistence.model.BienInmueble;

public interface BienInmuebleService {
    BienInmueble save(InmuebleDto bienInmuebleDto);

    Collection<InmuebleDto> findAll();

    InmuebleDto findById(Long id);

    void deleteById(Long id);

    boolean exist(Long id);
}

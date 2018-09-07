package mx.com.admoninmuebles.service;

import java.util.Collection;

import mx.com.admoninmuebles.dto.InmuebleDto;
import mx.com.admoninmuebles.persistence.model.Inmueble;

public interface InmuebleService {
    Inmueble save(InmuebleDto inmuebleDto);

    Collection<InmuebleDto> findAll();

    InmuebleDto findById(Long id);

    void deleteById(Long id);

    boolean exist(Long id);
}

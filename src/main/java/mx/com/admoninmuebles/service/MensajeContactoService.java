package mx.com.admoninmuebles.service;

import java.util.Collection;

import mx.com.admoninmuebles.dto.MensajeContactoDto;

public interface MensajeContactoService {
	
    MensajeContactoDto save(MensajeContactoDto mensajeContactoDto);
    MensajeContactoDto findById(Long id);
    Collection<MensajeContactoDto> findAll();
    void deleteById(Long id);
}

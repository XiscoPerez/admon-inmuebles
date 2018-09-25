package mx.com.admoninmuebles.service;

import java.util.Collection;

import mx.com.admoninmuebles.dto.MensajeContactoEstatusDto;

public interface MensajeContactoEstatusService {
	
	Collection<MensajeContactoEstatusDto> findAll();
	MensajeContactoEstatusDto findById(Long id);
	

}

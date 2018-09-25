package mx.com.admoninmuebles.service;

import java.util.Collection;

import mx.com.admoninmuebles.dto.SectorDto;

public interface SectorService {
	
	Collection<SectorDto> findAll();
	SectorDto findById(Long id);

}

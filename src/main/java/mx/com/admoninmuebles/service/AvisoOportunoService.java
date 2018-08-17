package mx.com.admoninmuebles.service;

import mx.com.admoninmuebles.dto.AvisoOportunoDto;
import mx.com.admoninmuebles.persistence.model.AvisoOportuno;

public interface AvisoOportunoService {
    AvisoOportuno save(AvisoOportunoDto avisoOportunoDto);
}

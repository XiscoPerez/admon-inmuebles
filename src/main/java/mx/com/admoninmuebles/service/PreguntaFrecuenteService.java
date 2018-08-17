package mx.com.admoninmuebles.service;

import mx.com.admoninmuebles.dto.PreguntaFrecuenteDto;
import mx.com.admoninmuebles.persistence.model.PreguntaFrecuente;

public interface PreguntaFrecuenteService {
    PreguntaFrecuente save(PreguntaFrecuenteDto preguntaFrecuenteDto);
}

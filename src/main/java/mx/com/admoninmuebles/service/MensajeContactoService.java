package mx.com.admoninmuebles.service;

import mx.com.admoninmuebles.dto.MensajeContactoDto;
import mx.com.admoninmuebles.persistence.model.MensajeContacto;

public interface MensajeContactoService {
    MensajeContacto save(MensajeContactoDto mensajeContactoDto);
}

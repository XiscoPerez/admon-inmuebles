package mx.com.admoninmuebles.service;

import mx.com.admoninmuebles.dto.TipoPagoBancarioDto;
import mx.com.admoninmuebles.persistence.model.TipoPagoBancario;

public interface TipoPagoBancarioService {
    TipoPagoBancario save(TipoPagoBancarioDto tipoPagoBancarioDto);
}

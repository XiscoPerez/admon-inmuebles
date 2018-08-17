package mx.com.admoninmuebles.service;

import mx.com.admoninmuebles.dto.TipoPagoDto;
import mx.com.admoninmuebles.persistence.model.TipoPago;

public interface TipoPagoService {
    TipoPago save(TipoPagoDto tipoTelefonoDto);
}

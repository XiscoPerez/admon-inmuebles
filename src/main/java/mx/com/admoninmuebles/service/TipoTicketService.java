package mx.com.admoninmuebles.service;

import mx.com.admoninmuebles.dto.TipoTicketDto;
import mx.com.admoninmuebles.persistence.model.TipoTicket;

public interface TipoTicketService {
    TipoTicket save(TipoTicketDto tipoTicketDto);
}

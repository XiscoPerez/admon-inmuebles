package mx.com.admoninmuebles.service;

import mx.com.admoninmuebles.dto.EstatusTicketDto;
import mx.com.admoninmuebles.persistence.model.EstatusTicket;

public interface EstatusTicketService {
    EstatusTicket save(EstatusTicketDto estatusTicketDto);
}

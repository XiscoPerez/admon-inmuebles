package mx.com.admoninmuebles.service;

import mx.com.admoninmuebles.dto.TicketDto;
import mx.com.admoninmuebles.persistence.model.Ticket;

public interface TicketService {
    Ticket save(TicketDto ticketDto);
}

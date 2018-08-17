package mx.com.admoninmuebles.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.admoninmuebles.dto.TicketDto;
import mx.com.admoninmuebles.persistence.model.Ticket;
import mx.com.admoninmuebles.persistence.repository.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Ticket save(final TicketDto ticketDto) {
        return ticketRepository.save(modelMapper.map(ticketDto, Ticket.class));
    }

}

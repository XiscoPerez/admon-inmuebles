package mx.com.admoninmuebles.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.admoninmuebles.dto.TipoTicketDto;
import mx.com.admoninmuebles.persistence.model.TipoTicket;
import mx.com.admoninmuebles.persistence.repository.TipoTicketRepository;

@Service
public class TipoTicketServiceImpl implements TipoTicketService {

    @Autowired
    private TipoTicketRepository tipoTicketRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TipoTicket save(final TipoTicketDto tipoTicketDto) {
        return tipoTicketRepository.save(modelMapper.map(tipoTicketDto, TipoTicket.class));
    }

}

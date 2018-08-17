package mx.com.admoninmuebles.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.admoninmuebles.dto.EstatusTicketDto;
import mx.com.admoninmuebles.persistence.model.EstatusTicket;
import mx.com.admoninmuebles.persistence.repository.EstatusTicketRepository;

@Service
public class EstatusTicketServiceImpl implements EstatusTicketService {

    @Autowired
    private EstatusTicketRepository estatusTicketRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public EstatusTicket save(final EstatusTicketDto estatusTicket) {
        return estatusTicketRepository.save(modelMapper.map(estatusTicket, EstatusTicket.class));
    }

}

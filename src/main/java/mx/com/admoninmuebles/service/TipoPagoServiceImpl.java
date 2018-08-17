package mx.com.admoninmuebles.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.admoninmuebles.dto.TipoPagoDto;
import mx.com.admoninmuebles.persistence.model.TipoPago;
import mx.com.admoninmuebles.persistence.repository.TipoPagoRepository;

@Service
public class TipoPagoServiceImpl implements TipoPagoService {

    @Autowired
    private TipoPagoRepository tipoPagoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TipoPago save(final TipoPagoDto tipoPagoDto) {
        return tipoPagoRepository.save(modelMapper.map(tipoPagoDto, TipoPago.class));
    }

}

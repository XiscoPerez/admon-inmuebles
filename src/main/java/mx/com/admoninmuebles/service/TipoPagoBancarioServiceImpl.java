package mx.com.admoninmuebles.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.admoninmuebles.dto.TipoPagoBancarioDto;
import mx.com.admoninmuebles.persistence.model.TipoPagoBancario;
import mx.com.admoninmuebles.persistence.repository.TipoPagoBancarioRepository;

@Service
public class TipoPagoBancarioServiceImpl implements TipoPagoBancarioService {

    @Autowired
    private TipoPagoBancarioRepository tipoPagoBancarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TipoPagoBancario save(final TipoPagoBancarioDto tipoPagoBancarioDto) {
        return tipoPagoBancarioRepository.save(modelMapper.map(tipoPagoBancarioDto, TipoPagoBancario.class));
    }

}

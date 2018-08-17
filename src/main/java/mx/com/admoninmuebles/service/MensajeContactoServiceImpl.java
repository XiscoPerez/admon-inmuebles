package mx.com.admoninmuebles.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.admoninmuebles.dto.MensajeContactoDto;
import mx.com.admoninmuebles.persistence.model.MensajeContacto;
import mx.com.admoninmuebles.persistence.repository.MensajeContactoRepository;

@Service
public class MensajeContactoServiceImpl implements MensajeContactoService {

    @Autowired
    private MensajeContactoRepository mensajeContactoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MensajeContacto save(final MensajeContactoDto mensajeContactoDto) {
        return mensajeContactoRepository.save(modelMapper.map(mensajeContactoDto, MensajeContacto.class));
    }

}

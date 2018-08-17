package mx.com.admoninmuebles.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.admoninmuebles.dto.PreguntaFrecuenteDto;
import mx.com.admoninmuebles.persistence.model.PreguntaFrecuente;
import mx.com.admoninmuebles.persistence.repository.PreguntaFrecuenteRepository;

@Service
public class PreguntaFrecuenteServiceImpl implements PreguntaFrecuenteService {

    @Autowired
    private PreguntaFrecuenteRepository preguntaFrecuenteRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PreguntaFrecuente save(final PreguntaFrecuenteDto preguntaFrecuenteDto) {
        return preguntaFrecuenteRepository.save(modelMapper.map(preguntaFrecuenteDto, PreguntaFrecuente.class));
    }

}

package mx.com.admoninmuebles.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.admoninmuebles.dto.AvisoOportunoDto;
import mx.com.admoninmuebles.persistence.model.AvisoOportuno;
import mx.com.admoninmuebles.persistence.repository.AvisoOportunoRepository;

@Service
public class AvisoOportunoServiceImpl implements AvisoOportunoService {

    @Autowired
    private AvisoOportunoRepository avisoOportunoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AvisoOportuno save(final AvisoOportunoDto avisoOportunoDto) {
        return avisoOportunoRepository.save(modelMapper.map(avisoOportunoDto, AvisoOportuno.class));
    }

}

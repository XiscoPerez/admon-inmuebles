package mx.com.admoninmuebles.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.admoninmuebles.dto.AreaServicioDto;
import mx.com.admoninmuebles.persistence.model.AreaServicio;
import mx.com.admoninmuebles.persistence.repository.AreaServicioRepository;

@Service
public class AreaServicioServiceImpl implements AreaServicioService {

    @Autowired
    private AreaServicioRepository AreaServicioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AreaServicio save(final AreaServicioDto userDto) {
        return AreaServicioRepository.save(modelMapper.map(userDto, AreaServicio.class));
    }

}

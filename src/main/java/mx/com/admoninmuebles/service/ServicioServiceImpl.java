package mx.com.admoninmuebles.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.admoninmuebles.dto.ServicioDto;
import mx.com.admoninmuebles.persistence.model.Servicio;
import mx.com.admoninmuebles.persistence.repository.ServicioRepository;

@Service
public class ServicioServiceImpl implements ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Servicio save(final ServicioDto servicioDto) {
        return servicioRepository.save(modelMapper.map(servicioDto, Servicio.class));
    }

}

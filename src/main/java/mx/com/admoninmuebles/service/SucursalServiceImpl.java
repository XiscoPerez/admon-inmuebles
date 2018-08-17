package mx.com.admoninmuebles.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.admoninmuebles.dto.SucursalDto;
import mx.com.admoninmuebles.persistence.model.Sucursal;
import mx.com.admoninmuebles.persistence.repository.SucursalRepository;

@Service
public class SucursalServiceImpl implements SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Sucursal save(final SucursalDto sucursalDto) {
        return sucursalRepository.save(modelMapper.map(sucursalDto, Sucursal.class));
    }

}

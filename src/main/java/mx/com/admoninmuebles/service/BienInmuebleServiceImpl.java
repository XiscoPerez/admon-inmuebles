package mx.com.admoninmuebles.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.admoninmuebles.dto.BienInmuebleDto;
import mx.com.admoninmuebles.persistence.model.BienInmueble;
import mx.com.admoninmuebles.persistence.repository.BienInmuebleRepository;

@Service
public class BienInmuebleServiceImpl implements BienInmuebleService {

    @Autowired
    private BienInmuebleRepository bienInmuebleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BienInmueble save(final BienInmuebleDto bienInmuebleDto) {
        return bienInmuebleRepository.save(modelMapper.map(bienInmuebleDto, BienInmueble.class));
    }

}

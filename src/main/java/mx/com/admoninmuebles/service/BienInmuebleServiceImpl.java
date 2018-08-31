package mx.com.admoninmuebles.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.admoninmuebles.dto.InmuebleDto;
import mx.com.admoninmuebles.persistence.model.BienInmueble;
import mx.com.admoninmuebles.persistence.repository.BienInmuebleRepository;
import mx.com.admoninmuebles.persistence.repository.DatosAdicionalesRepository;
import mx.com.admoninmuebles.persistence.repository.DireccionRepository;

@Service
public class BienInmuebleServiceImpl implements BienInmuebleService {

    @Autowired
    private BienInmuebleRepository bienInmuebleRepository;

    @Autowired
    private DireccionRepository direccionRepository;
    @Autowired
    private DatosAdicionalesRepository datosAdicionalesRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BienInmueble save(final InmuebleDto bienInmuebleDto) {
        BienInmueble bienInmueble = modelMapper.map(bienInmuebleDto, BienInmueble.class);
        bienInmueble.setDireccion(direccionRepository.save(bienInmueble.getDireccion()));
        bienInmueble.setDatosAdicionales(datosAdicionalesRepository.save(bienInmueble.getDatosAdicionales()));
        return bienInmuebleRepository.save(bienInmueble);
    }

    @Override
    public Collection<InmuebleDto> findAll() {
        return StreamSupport.stream(bienInmuebleRepository.findAll().spliterator(), false).map(zona -> modelMapper.map(zona, InmuebleDto.class)).collect(Collectors.toList());
    }

    @Override
    public InmuebleDto findById(final Long id) {
        Optional<BienInmueble> zona = bienInmuebleRepository.findById(id);
        return modelMapper.map(zona.get(), InmuebleDto.class);
    }

    @Override
    public void deleteById(final Long id) {
        bienInmuebleRepository.deleteById(id);

    }

    @Override
    public boolean exist(final Long id) {
        return bienInmuebleRepository.existsById(id);
    }

}

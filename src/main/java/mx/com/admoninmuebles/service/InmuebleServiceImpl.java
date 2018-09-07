package mx.com.admoninmuebles.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.admoninmuebles.dto.InmuebleDto;
import mx.com.admoninmuebles.persistence.model.Inmueble;
import mx.com.admoninmuebles.persistence.repository.DatosAdicionalesRepository;
import mx.com.admoninmuebles.persistence.repository.DireccionRepository;
import mx.com.admoninmuebles.persistence.repository.InmuebleRepository;

@Service
public class InmuebleServiceImpl implements InmuebleService {
    @Autowired
    private InmuebleRepository inmuebleRepository;
    @Autowired
    private DireccionRepository direccionRepository;
    @Autowired
    private DatosAdicionalesRepository datosAdicionalesRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Inmueble save(final InmuebleDto inmuebleDto) {
        Inmueble inmueble = modelMapper.map(inmuebleDto, Inmueble.class);
        inmueble.setDireccion(direccionRepository.save(inmueble.getDireccion()));
        inmueble.setDatosAdicionales(datosAdicionalesRepository.save(inmueble.getDatosAdicionales()));
        return inmuebleRepository.save(inmueble);
    }

    @Override
    public Collection<InmuebleDto> findAll() {
        return StreamSupport.stream(inmuebleRepository.findAll().spliterator(), false).map(zona -> modelMapper.map(zona, InmuebleDto.class)).collect(Collectors.toList());
    }

    @Override
    public InmuebleDto findById(final Long id) {
        Optional<Inmueble> zona = inmuebleRepository.findById(id);
        return modelMapper.map(zona.get(), InmuebleDto.class);
    }

    @Override
    public void deleteById(final Long id) {
        inmuebleRepository.deleteById(id);

    }

    @Override
    public boolean exist(final Long id) {
        return inmuebleRepository.existsById(id);
    }

}

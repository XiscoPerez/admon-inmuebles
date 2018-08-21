package mx.com.admoninmuebles.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.admoninmuebles.dto.ZonaDto;
import mx.com.admoninmuebles.persistence.model.Zona;
import mx.com.admoninmuebles.persistence.repository.ZonaRepository;

@Service
public class ZonaServiceImpl implements ZonaService {

    @Autowired
    private ZonaRepository zonaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Zona save(final ZonaDto zonaDto) {
        return zonaRepository.save(modelMapper.map(zonaDto, Zona.class));
    }

    @Override
    public Optional<ZonaDto> findById(final Long id) {
        Optional<ZonaDto> optZonaDto = Optional.empty();
        Optional<Zona> optZona = zonaRepository.findById(id);
        if (optZona.isPresent()) {
            optZonaDto = Optional.of(modelMapper.map(optZona.get(), ZonaDto.class));
        }
        return optZonaDto;
    }

}

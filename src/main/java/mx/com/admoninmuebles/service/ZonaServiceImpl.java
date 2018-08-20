package mx.com.admoninmuebles.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
	public Collection<ZonaDto> findAll() {
		return StreamSupport.stream(zonaRepository.findAll().spliterator(), false)
				.map(zona -> modelMapper.map(zona, ZonaDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public ZonaDto findById(Long idZona) {
		Optional<Zona> zona = zonaRepository.findById(idZona);
		return modelMapper.map(zona.get(), ZonaDto.class);
	}

	@Override
	public void deleteById(Long idZona) {
		zonaRepository.deleteById(idZona);
		
	}

}

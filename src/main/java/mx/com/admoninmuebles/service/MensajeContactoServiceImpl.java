package mx.com.admoninmuebles.service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.admoninmuebles.dto.MensajeContactoDto;
import mx.com.admoninmuebles.persistence.model.MensajeContacto;
import mx.com.admoninmuebles.persistence.repository.MensajeContactoRepository;

@Service
public class MensajeContactoServiceImpl implements MensajeContactoService {

    @Autowired
    private MensajeContactoRepository mensajeContactoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MensajeContactoDto save(final MensajeContactoDto mensajeContactoDto) {
    	
    	MensajeContacto MensajeContactoCreado =  mensajeContactoRepository.save(modelMapper.map(mensajeContactoDto, MensajeContacto.class));
    	
    	return modelMapper.map(MensajeContactoCreado, MensajeContactoDto.class);
    }

	@Override
	public MensajeContactoDto findById(Long id) {
		return modelMapper.map(mensajeContactoRepository.findById(id).get(), MensajeContactoDto.class);
	}

	@Override
	public Collection<MensajeContactoDto> findAll() {
		 return StreamSupport.stream(mensajeContactoRepository.findAll().spliterator(), false).map(mensajeContacto -> modelMapper.map(mensajeContacto, MensajeContactoDto.class)).collect(Collectors.toList());
	}

	@Override
	public void deleteById(Long id) {
		mensajeContactoRepository.deleteById(id);
	}

}

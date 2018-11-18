package mx.com.admoninmuebles.service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import mx.com.admoninmuebles.constant.PlantillaCorreoConst;
import mx.com.admoninmuebles.dto.CorreoDto;
import mx.com.admoninmuebles.dto.MensajeContactoDto;
import mx.com.admoninmuebles.persistence.model.MensajeContacto;
import mx.com.admoninmuebles.persistence.repository.MensajeContactoEstatusRepository;
import mx.com.admoninmuebles.persistence.repository.MensajeContactoRepository;
import mx.com.admoninmuebles.persistence.repository.SectorRepository;

@Service
public class MensajeContactoServiceImpl implements MensajeContactoService {

    @Autowired
    private MensajeContactoRepository mensajeContactoRepository;
    
    @Autowired
    private SectorRepository sectorRepository;
    
    @Autowired
    private MensajeContactoEstatusRepository mensajeContactoEstatusRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private CorreoService correoService;

    @Override
    public MensajeContactoDto save(final MensajeContactoDto mensajeContactoDto) {
    	
    	MensajeContacto MensajeContactoCreado =  mensajeContactoRepository.save(modelMapper.map(mensajeContactoDto, MensajeContacto.class));
    	
    	Context datosPlantilla = new Context();
    	datosPlantilla.setVariable("nombre", mensajeContactoDto.getNombre());
    	datosPlantilla.setVariable("telefono", mensajeContactoDto.getTelefono());
    	datosPlantilla.setVariable("estado", mensajeContactoDto.getEstadoId());
    	datosPlantilla.setVariable("mensaje", mensajeContactoDto.getMensaje());
		
    	CorreoDto correoDto = new CorreoDto();
    	correoDto.setAsunto("Información de Gesco");
    	correoDto.setPara(mensajeContactoDto.getCorreo());
    	correoDto.setPlantilla(PlantillaCorreoConst.CONTACTANOS);
    	correoDto.setDe("bi.prueba.infinita@gmail.com");
    	correoDto.setDatosPlantilla(datosPlantilla);
    	correoService.enviarCorreo(correoDto);
    	
    	return modelMapper.map(MensajeContactoCreado, MensajeContactoDto.class);
    }
    
    @Override
    public MensajeContactoDto update(final MensajeContactoDto mensajeContactoDto) {
    	
    	MensajeContacto MensajeContactoCreado =  mensajeContactoRepository.save(modelMapper.map(mensajeContactoDto, MensajeContacto.class));
    	MensajeContactoCreado.setSector(sectorRepository.findById(mensajeContactoDto.getSectorId()).get());
    	MensajeContactoCreado.setMensajeContactoEstatus(mensajeContactoEstatusRepository.findById(mensajeContactoDto.getMensajeContactoEstatusId()).get());
    	
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

package mx.com.admoninmuebles.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.admoninmuebles.dto.NotificacionDto;
import mx.com.admoninmuebles.persistence.model.Notificacion;
import mx.com.admoninmuebles.persistence.repository.NotificacionRepository;

@Service
public class NotificacionServiceImpl implements NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Notificacion save(final NotificacionDto notificacionDto) {
        return notificacionRepository.save(modelMapper.map(notificacionDto, Notificacion.class));
    }

	@Override
	public Collection<NotificacionDto> findAll() {
		return StreamSupport.stream(notificacionRepository.findAll().spliterator(), false)
				.map(notificacion -> modelMapper.map(notificacion, NotificacionDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public NotificacionDto findById(Long idNotificacion) {
		Optional<Notificacion> notificacion = notificacionRepository.findById(idNotificacion);
		return modelMapper.map(notificacion.get(), NotificacionDto.class);
	}

	@Override
	public void deleteById(Long idNotificacion) {
		notificacionRepository.deleteById(idNotificacion);
		
	}

}

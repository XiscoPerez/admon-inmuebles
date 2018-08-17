package mx.com.admoninmuebles.service;

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

}

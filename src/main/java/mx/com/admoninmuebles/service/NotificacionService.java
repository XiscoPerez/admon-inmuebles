package mx.com.admoninmuebles.service;

import java.util.Collection;

import mx.com.admoninmuebles.dto.NotificacionDto;
import mx.com.admoninmuebles.persistence.model.Notificacion;

public interface NotificacionService {
    Notificacion save(NotificacionDto notificacionDto);
    Collection<NotificacionDto> findAll();
    NotificacionDto findById(Long idNotificacion);
    Collection<NotificacionDto> findByInmuebleId(Long id);
    void deleteById(Long idNotificacion);
}

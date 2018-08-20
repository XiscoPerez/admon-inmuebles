package mx.com.admoninmuebles.service;

import java.util.Collection;

import mx.com.admoninmuebles.dto.AvisoOportunoDto;
import mx.com.admoninmuebles.dto.NotificacionDto;
import mx.com.admoninmuebles.persistence.model.Notificacion;

public interface NotificacionService {
    Notificacion save(NotificacionDto notificacionDto);
    Collection<NotificacionDto> findAll();
    NotificacionDto findById(Long idNotificacion);
    void deleteById(Long idNotificacion);
}

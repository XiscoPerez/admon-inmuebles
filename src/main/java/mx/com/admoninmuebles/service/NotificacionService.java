package mx.com.admoninmuebles.service;

import mx.com.admoninmuebles.dto.NotificacionDto;
import mx.com.admoninmuebles.persistence.model.Notificacion;

public interface NotificacionService {
    Notificacion save(NotificacionDto notificacionDto);
}

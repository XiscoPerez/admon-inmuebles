package mx.com.admoninmuebles.persistence.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.com.admoninmuebles.persistence.model.Notificacion;

@Repository
public interface NotificacionRepository extends CrudRepository<Notificacion, Long> {
	Collection<Notificacion> findByInmuebleId(Long id);
	Collection<Notificacion> findByInmuebleIdAndFechaExposicionInicialBeforeAndFechaExposicionFinalAfter(Long id, Date today, Date todayf);
	Collection<Notificacion> findByInmuebleIdAndFechaExposicionInicialLessThanEqualAndFechaExposicionFinalGreaterThanEqual(Long id, LocalDate today, LocalDate todayf);

}

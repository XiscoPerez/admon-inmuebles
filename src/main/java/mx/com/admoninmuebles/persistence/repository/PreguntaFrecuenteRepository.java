package mx.com.admoninmuebles.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.com.admoninmuebles.persistence.model.PreguntaFrecuente;

@Repository
public interface PreguntaFrecuenteRepository extends CrudRepository<PreguntaFrecuente, Long> {

}

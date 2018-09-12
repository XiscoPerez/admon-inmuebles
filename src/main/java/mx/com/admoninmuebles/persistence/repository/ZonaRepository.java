package mx.com.admoninmuebles.persistence.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.com.admoninmuebles.persistence.model.Zona;

@Repository
public interface ZonaRepository extends CrudRepository<Zona, String> {
	
	Collection<Zona> findByAdminZonaId(Long id);

}

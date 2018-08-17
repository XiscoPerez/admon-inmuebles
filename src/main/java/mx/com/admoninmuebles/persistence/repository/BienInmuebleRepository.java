package mx.com.admoninmuebles.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.com.admoninmuebles.persistence.model.BienInmueble;

@Repository
public interface BienInmuebleRepository extends CrudRepository<BienInmueble, String> {

}

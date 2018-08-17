package mx.com.admoninmuebles.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.com.admoninmuebles.persistence.model.Pago;

@Repository
public interface PagoRepository extends CrudRepository<Pago, String>  {

}

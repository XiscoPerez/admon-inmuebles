package mx.com.admoninmuebles.persistence.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.com.admoninmuebles.persistence.model.Rol;

@Repository
public interface RolRepository extends CrudRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
}

package mx.com.admoninmuebles.persistence.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.com.admoninmuebles.persistence.model.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByCorreo(String correo);

    Collection<Usuario> findByRolesNombre(String nombre);

    Collection<Usuario> findByRolesNombreAndAreasServicioId(String nombre, Long id);

}

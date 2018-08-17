package mx.com.admoninmuebles.persistence.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.com.admoninmuebles.persistence.model.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, String> {

    Optional<Usuario> findByUsername(String username);

}

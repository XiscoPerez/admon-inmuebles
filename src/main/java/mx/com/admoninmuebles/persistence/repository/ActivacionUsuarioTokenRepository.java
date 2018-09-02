package mx.com.admoninmuebles.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import mx.com.admoninmuebles.persistence.model.ActivacionUsuarioToken;

public interface ActivacionUsuarioTokenRepository extends CrudRepository<ActivacionUsuarioToken, Long>{
	
	Optional<ActivacionUsuarioToken> findByToken(String token);
}

package mx.com.admoninmuebles.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.com.admoninmuebles.persistence.model.RecuperacionContraseniaToken;

public interface RecuperacionContraseniaTokenRepository extends JpaRepository<RecuperacionContraseniaToken, Long>{
	
	Optional<RecuperacionContraseniaToken> findByToken(String token);
}

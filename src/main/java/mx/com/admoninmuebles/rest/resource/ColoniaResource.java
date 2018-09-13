package mx.com.admoninmuebles.rest.resource;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.com.admoninmuebles.dto.ColoniaDto;
import mx.com.admoninmuebles.service.ColoniaService;

@RestController
@RequestMapping("/api")
public class ColoniaResource {
	
    @Autowired
    private ColoniaService coloniaService;
    
	@GetMapping("/colonias")
	public ResponseEntity<Collection<ColoniaDto>> buscarColonias(@RequestParam(name = "codigoPostal", required = false) String codigoPostal , @RequestParam(name = "zonaCodigo", required = false) String zonaCodigo) {
		try {
			Collection<ColoniaDto> colonias = null;
			if( (codigoPostal != null && !codigoPostal.isEmpty()) && (zonaCodigo != null && !zonaCodigo.isEmpty())) {
				colonias = coloniaService.findBycodigoPostalAndZonaCodigo(codigoPostal, zonaCodigo);
			} else if(codigoPostal != null && !codigoPostal.isEmpty()){
				colonias = coloniaService.findBycodigoPostal(codigoPostal);
			} else if(zonaCodigo != null && !zonaCodigo.isEmpty()){
				colonias = coloniaService.findByZonaCodigo(zonaCodigo);
			}else {
				colonias = coloniaService.findAll();
			}
			return new ResponseEntity<>(colonias, HttpStatus.OK);
		} catch(UsernameNotFoundException e) {
			return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
		}
		
	}
	
}

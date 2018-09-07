package mx.com.admoninmuebles.rest.resource;

import java.util.Collection;

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
	public ResponseEntity<Collection<ColoniaDto>> existeUsuario(@RequestParam("codigoPostal") String codigoPostal) {
		try {
			Collection<ColoniaDto> colonias = coloniaService.findBycodigoPostal(codigoPostal);
			return new ResponseEntity<>(colonias, HttpStatus.OK);
		} catch(UsernameNotFoundException e) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		
	}
	
	

}

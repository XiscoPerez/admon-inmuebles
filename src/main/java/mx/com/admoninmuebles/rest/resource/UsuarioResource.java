package mx.com.admoninmuebles.rest.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.admoninmuebles.service.UsuarioService;

@RestController
@RequestMapping("/api")
public class UsuarioResource {
	
    @Autowired
    private UsuarioService userService;

	@GetMapping("/usuarios/{login}")
	public ResponseEntity<String> existeUsuario(@PathVariable String login) {
		System.out.println("Iniciando validacion usuario");
		try {
			userService.findByUsernameOrCorreo(login);
		} catch(UsernameNotFoundException e) {
			return new ResponseEntity<String>(Boolean.FALSE.toString(), HttpStatus.OK);
		}
		
		return new ResponseEntity<String>(Boolean.TRUE.toString(), HttpStatus.OK);
	}

}

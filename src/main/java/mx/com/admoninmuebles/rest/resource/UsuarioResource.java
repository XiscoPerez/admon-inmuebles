package mx.com.admoninmuebles.rest.resource;


import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.admoninmuebles.dto.CambioContraseniaDto;
import mx.com.admoninmuebles.error.BusinessException;
import mx.com.admoninmuebles.service.UsuarioService;

@RestController
@RequestMapping("/api")
public class UsuarioResource {
	
    @Autowired
    private UsuarioService userService;
    
    @Autowired
    private MessageSource messages;

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
	
    @PostMapping(value = "/usuarios/cambioContrasenia")
    public ResponseEntity<String> cambiarContrasenia(final Locale locale, final Model model, @Valid final CambioContraseniaDto cambioContraseniaDto) {
    	
    	try {
    		userService.cambiarContrasenia(cambioContraseniaDto);
    		return new ResponseEntity<String>(messages.getMessage("usuario.actualiza.contrasenia.ok", null, locale), HttpStatus.OK);
    	} catch(BusinessException e) {
    		return new ResponseEntity<String>(messages.getMessage(e.getMessage(), null, locale), HttpStatus.BAD_REQUEST);
    	}
    	
    }

}

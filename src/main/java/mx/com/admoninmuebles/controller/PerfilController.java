package mx.com.admoninmuebles.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PerfilController {
	
    @GetMapping(value = "/perfil")
    public String init() {
        return "user/perfil";
    }

}

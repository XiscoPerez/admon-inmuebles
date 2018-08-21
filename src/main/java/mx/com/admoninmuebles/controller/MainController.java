
package mx.com.admoninmuebles.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/user/index")
    public String userIndex() {
        return "user/index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    
    @RequestMapping("/contacto")
    public String contacto() {
        return "contacto";
    }
    
    @RequestMapping("/nuestros-clientes")
    public String nuestrosClientes() {
        return "nuestros-clientes";
    }
    
    @RequestMapping("/quienes-somos")
    public String quienesSomos() {
        return "quienes-somos";
    }
    
    @RequestMapping("/servicios")
    public String servicios() {
        return "servicios";
    }
    
    @RequestMapping("/ventajas-competitivas")
    public String ventajasCompetitivas() {
        return "ventajas-competitivas";
    }

}

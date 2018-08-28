
package mx.com.admoninmuebles.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String root() {
        return "redirect:/inicio";
    }

    @RequestMapping("/inicio")
    public String inicio() {
        return "inicio";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/contacto")
    public String contacto() {
        return "/paginas/estaticas/contacto";
    }

    @RequestMapping("/nuestros-clientes")
    public String nuestrosClientes() {
        return "/paginas/estaticas/nuestros-clientes";
    }

    @RequestMapping("/quienes-somos")
    public String quienesSomos() {
        return "/paginas/estaticas/quienes-somos";
    }

    @RequestMapping("/servicios")
    public String servicios() {
        return "/paginas/estaticas/servicios";
    }

    @RequestMapping("/ventajas-competitivas")
    public String ventajasCompetitivas() {
        return "/paginas/estaticas/ventajas-competitivas";
    }

    @RequestMapping(value = "/acceso-denegado")
    public String accesoDenegado(final ModelMap model) {
        return "acceso-denegado";
    }
    
    @RequestMapping("/sucursales")
    public String sucursales() {
        return "/paginas/estaticas/sucursales";
    }

}

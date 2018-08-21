package mx.com.admoninmuebles.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import mx.com.admoninmuebles.dto.NotificacionDto;
import mx.com.admoninmuebles.service.NotificacionService;

@Controller
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping(value = "/crearNotificacion")
    public String showForm(final NotificacionDto notificacionDto) {
        return "crearNotificacion";
    }

    @PostMapping(value = "/crearNotificacion")
    public String crearNotificacion(final Locale locale, final Model model, @Valid final NotificacionDto notificacionDto, final BindingResult bindingResult) {
        notificacionService.save(notificacionDto);
        return "redirect:/crearNotificacion";
    }
    
    @GetMapping(value = "/catalogos/notificacion")
    public String init(final NotificacionDto notificacionDto, Model model) {
    	
    	model.addAttribute("notificaciones", notificacionService.findAll());
    	return "catalogos/notificacion";
    }
    
    @PostMapping(value = "/catalogos/notificacion")
    public String guardarNotificacion(final Locale locale, final Model model, @Valid final NotificacionDto notificacionDto, final BindingResult bindingResult) {
    	notificacionService.save(notificacionDto);
        return "redirect:/catalogos/notificacion";
    }
    
    @GetMapping(value = "/catalogos/notificacion-editar/{idNotificacion}")
    public String buscarNotificacionPorId(final @PathVariable long idNotificacion, Model model) {
    	model.addAttribute("notificacionDto", notificacionService.findById(idNotificacion));
        return "catalogos/notificacion-edicion";
    }
    
    @PostMapping(value = "/catalogos/notificacion-editar")
    public String editarNotificacion(final Locale locale, final Model model, @Valid final NotificacionDto notificacionDto, final BindingResult bindingResult) {
    	notificacionService.save(notificacionDto);
        return "redirect:/catalogos/notificacion";
    }
    
    @GetMapping(value = "/catalogos/notificacion-eliminar/{idNotificacion}")
    public String eliminarNotificacion(final @PathVariable long idNotificacion) {
    	notificacionService.deleteById(idNotificacion);;
        return "redirect:/catalogos/notificacion";
    }

}

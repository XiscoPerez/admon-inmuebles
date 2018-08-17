package mx.com.admoninmuebles.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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

}

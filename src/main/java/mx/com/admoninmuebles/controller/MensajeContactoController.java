package mx.com.admoninmuebles.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import mx.com.admoninmuebles.dto.MensajeContactoDto;
import mx.com.admoninmuebles.service.MensajeContactoService;

@Controller
public class MensajeContactoController {

    @Autowired
    private MensajeContactoService mensajeContactoService;

    @GetMapping(value = "/crearMensajeContacto")
    public String showForm(final MensajeContactoDto mensajeContactoDto) {
        return "crearMensajeContacto";
    }

    @PostMapping(value = "/crearMensajeContacto")
    public String crearMensajeContacto(final Locale locale, final Model model, @Valid final MensajeContactoDto mensajeContactoDto, final BindingResult bindingResult) {
        mensajeContactoService.save(mensajeContactoDto);
        return "redirect:/crearMensajeContacto";
    }

}

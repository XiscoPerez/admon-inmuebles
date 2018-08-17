package mx.com.admoninmuebles.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import mx.com.admoninmuebles.dto.DireccionDto;
import mx.com.admoninmuebles.service.DireccionService;

@Controller
public class DireccionController {

    @Autowired
    private DireccionService direccionService;

    @GetMapping(value = "/crearDireccion")
    public String showForm(final DireccionDto direccionDto) {
        return "crearDireccion";
    }

    @PostMapping(value = "/crearDireccion")
    public String crearDireccion(final Locale locale, final Model model, @Valid final DireccionDto direccionDto, final BindingResult bindingResult) {
        direccionService.save(direccionDto);
        return "redirect:/crearDireccion";
    }

}

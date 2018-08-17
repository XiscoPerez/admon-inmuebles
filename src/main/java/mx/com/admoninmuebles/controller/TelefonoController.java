package mx.com.admoninmuebles.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import mx.com.admoninmuebles.dto.TelefonoDto;
import mx.com.admoninmuebles.service.TelefonoService;

@Controller
public class TelefonoController {

    @Autowired
    private TelefonoService telefonoService;

    @GetMapping(value = "/crearTelefono")
    public String showForm(final TelefonoDto telefonoDto) {
        return "crearTelefono";
    }

    @PostMapping(value = "/crearTelefono")
    public String crearTelefono(final Locale locale, final Model model, @Valid final TelefonoDto telefonoDto, final BindingResult bindingResult) {
        telefonoService.save(telefonoDto);
        return "redirect:/crearTelefono";
    }

}

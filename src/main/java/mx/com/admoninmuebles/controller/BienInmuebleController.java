package mx.com.admoninmuebles.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import mx.com.admoninmuebles.dto.BienInmuebleDto;
import mx.com.admoninmuebles.service.BienInmuebleService;

@Controller
public class BienInmuebleController {

    @Autowired
    private BienInmuebleService bienInmuebleService;

    @GetMapping(value = "/crearBienInmueble")
    public String showForm(final BienInmuebleDto bienInmuebleDto) {
        return "crearBienInmueble";
    }

    @PostMapping(value = "/crearBienInmueble")
    public String crearBienInmueble(final Locale locale, final Model model, @Valid final BienInmuebleDto bienInmuebleDto, final BindingResult bindingResult) {
        bienInmuebleService.save(bienInmuebleDto);
        return "redirect:/crearBienInmueble";
    }

}

package mx.com.admoninmuebles.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import mx.com.admoninmuebles.dto.AvisoOportunoDto;
import mx.com.admoninmuebles.service.AvisoOportunoService;

@Controller
public class AvisoOportunoController {

    @Autowired
    private AvisoOportunoService avisoOportunoService;

    @GetMapping(value = "/crearAvisoOportuno")
    public String showForm(final AvisoOportunoDto avisoOportunoDto) {
        return "crearAvisoOportuno";
    }

    @PostMapping(value = "/crearAvisoOportuno")
    public String crearAvisoOportuno(final Locale locale, final Model model, @Valid final AvisoOportunoDto avisoOportunoDto, final BindingResult bindingResult) {
        avisoOportunoService.save(avisoOportunoDto);
        return "redirect:/crearAvisoOportuno";
    }

}

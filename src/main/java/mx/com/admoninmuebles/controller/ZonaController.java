package mx.com.admoninmuebles.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mx.com.admoninmuebles.dto.ZonaDto;
import mx.com.admoninmuebles.service.ZonaService;

@Controller
public class ZonaController {

    @Autowired
    private ZonaService zonaService;

    @RequestMapping(value = "/crearZona", method = RequestMethod.GET)
    public String showForm(final ZonaDto zonaDto) {
        return "crearZona";
    }

    @RequestMapping(value = "/crearZona", method = RequestMethod.POST)
    public String signUp(final Locale locale, final Model model, @Valid final ZonaDto zonaDto, final BindingResult bindingResult) {
        zonaService.save(zonaDto);
        return "redirect:/crearZona";
    }

}

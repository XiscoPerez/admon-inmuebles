package mx.com.admoninmuebles.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import mx.com.admoninmuebles.dto.AreaServicioDto;
import mx.com.admoninmuebles.service.AreaServicioService;

@Controller
public class AreaServicioController {

    @Autowired
    private AreaServicioService areaServicioService;

    @GetMapping(value = "/crearAreaServicio")
    public String showForm(final AreaServicioDto areaServicioDto) {
        return "crearAreaServicio";
    }

    @PostMapping(value = "/crearAreaServicio")
    public String crearAreaServicio(final Locale locale, final Model model, @Valid final AreaServicioDto areaServicioDto, final BindingResult bindingResult) {
        areaServicioService.save(areaServicioDto);
        return "redirect:/crearAreaServicio";
    }

}

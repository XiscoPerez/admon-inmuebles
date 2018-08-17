package mx.com.admoninmuebles.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import mx.com.admoninmuebles.dto.ServicioDto;
import mx.com.admoninmuebles.service.ServicioService;

@Controller
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @GetMapping(value = "/crearServicio")
    public String showForm(final ServicioDto servicioDto) {
        return "crearServicio";
    }

    @PostMapping(value = "/crearServicio")
    public String crearServicio(final Locale locale, final Model model, @Valid final ServicioDto servicioDto, final BindingResult bindingResult) {
        servicioService.save(servicioDto);
        return "redirect:/crearServicio";
    }

}

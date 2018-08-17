package mx.com.admoninmuebles.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import mx.com.admoninmuebles.dto.SucursalDto;
import mx.com.admoninmuebles.service.SucursalService;

@Controller
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping(value = "/crearSucursal")
    public String showForm(final SucursalDto sucursalDto) {
        return "crearSucursal";
    }

    @PostMapping(value = "/crearSucursal")
    public String crearSucursal(final Locale locale, final Model model, @Valid final SucursalDto sucursalDto, final BindingResult bindingResult) {
        sucursalService.save(sucursalDto);
        return "redirect:/crearSucursal";
    }

}

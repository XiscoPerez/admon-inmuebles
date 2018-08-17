package mx.com.admoninmuebles.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import mx.com.admoninmuebles.dto.DatosAdicionalesDto;
import mx.com.admoninmuebles.service.DatosAdicionalesService;

@Controller
public class DatosAdicionalesController {

    @Autowired
    private DatosAdicionalesService datosAdicionalesService;

    @GetMapping(value = "/crearDatosAdicionales")
    public String showForm(final DatosAdicionalesDto datosAdicionalesDto) {
        return "crearDatosAdicionales";
    }

    @PostMapping(value = "/crearDatosAdicionales")
    public String crearDatosAdicionales(final Locale locale, final Model model, @Valid final DatosAdicionalesDto datosAdicionalesDto, final BindingResult bindingResult) {
        datosAdicionalesService.save(datosAdicionalesDto);
        return "redirect:/crearDatosAdicionales";
    }

}

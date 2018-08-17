package mx.com.admoninmuebles.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import mx.com.admoninmuebles.dto.CambioTicketDto;
import mx.com.admoninmuebles.service.CambioTicketService;

@Controller
public class CambioTicketController {

    @Autowired
    private CambioTicketService cambioTicketService;

    @GetMapping(value = "/crearCambioTicket")
    public String showForm(final CambioTicketDto cambioTicketDto) {
        return "crearCambioTicket";
    }

    @PostMapping(value = "/crearCambioTicket")
    public String crearCambioTicket(final Locale locale, final Model model, @Valid final CambioTicketDto cambioTicketDto, final BindingResult bindingResult) {
        cambioTicketService.save(cambioTicketDto);
        return "redirect:/crearCambioTicket";
    }

}

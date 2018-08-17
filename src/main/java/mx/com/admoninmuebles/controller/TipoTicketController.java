package mx.com.admoninmuebles.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import mx.com.admoninmuebles.dto.TipoTicketDto;
import mx.com.admoninmuebles.service.TipoTicketService;

@Controller
public class TipoTicketController {

    @Autowired
    private TipoTicketService tipoTicketService;

    @GetMapping(value = "/crearTipoTicket")
    public String showForm(final TipoTicketDto tipoTicketDto) {
        return "crearTipoTicket";
    }

    @PostMapping(value = "/crearTipoTicket")
    public String crearTipoTicket(final Locale locale, final Model model, @Valid final TipoTicketDto tipoTicketDto, final BindingResult bindingResult) {
        tipoTicketService.save(tipoTicketDto);
        return "redirect:/crearTipoTicket";
    }

}

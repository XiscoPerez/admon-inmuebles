package mx.com.admoninmuebles.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import mx.com.admoninmuebles.dto.TicketDto;
import mx.com.admoninmuebles.service.TicketService;

@Controller
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping(value = "/crearTicket")
    public String showForm(final TicketDto ticketDto) {
        return "crearTicket";
    }

    @PostMapping(value = "/crearTicket")
    public String crearTicket(final Locale locale, final Model model, @Valid final TicketDto ticketDto, final BindingResult bindingResult) {
        ticketService.save(ticketDto);
        return "redirect:/crearTicket";
    }

}

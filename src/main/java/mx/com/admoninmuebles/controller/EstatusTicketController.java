package mx.com.admoninmuebles.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import mx.com.admoninmuebles.dto.EstatusTicketDto;
import mx.com.admoninmuebles.service.EstatusTicketService;

@Controller
public class EstatusTicketController {

    @Autowired
    private EstatusTicketService estatusTicketService;

    @GetMapping(value = "/crearEstatusTicket")
    public String showForm(final EstatusTicketDto estatusTicketDto) {
        return "crearEstatusTicket";
    }

    @PostMapping(value = "/crearEstatusTicket")
    public String crearEstatusTicket(final Locale locale, final Model model, @Valid final EstatusTicketDto estatusTicketDto, final BindingResult bindingResult) {
        estatusTicketService.save(estatusTicketDto);
        return "redirect:/crearEstatusTicket";
    }

}

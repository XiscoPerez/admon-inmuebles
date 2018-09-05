package mx.com.admoninmuebles.controller;

import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import mx.com.admoninmuebles.dto.TicketDto;
import mx.com.admoninmuebles.security.SecurityUtils;
import mx.com.admoninmuebles.service.AreaServicioService;
import mx.com.admoninmuebles.service.TicketService;

@Controller
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private AreaServicioService areaServicioService;

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI', 'SOCIO_BI')")
    @GetMapping(value = "/tickets")
    public String init(final TicketDto ticketDto, final Model model) {
        model.addAttribute("tickets", ticketService.findAll());
        return "/ticket/tickets";
    }

    @PreAuthorize("hasRole('SOCIO_BI')")
    @GetMapping(value = "/mis-tickets")
    public String misTickets(final TicketDto ticketDto, final Model model) {
        Optional<Long> optId = SecurityUtils.getCurrentUserId();
        if (optId.isPresent()) {
            model.addAttribute("tickets", ticketService.findByUsuarioCreadorId(optId.get()));
        }
        return "/ticket/tickets";
    }

    @PreAuthorize("hasAuthority('INICIAR_TICKET')")
    @GetMapping(value = "/ticket-crear")
    public String ticketCrear(final TicketDto ticketDto, final HttpSession session) {
        session.setAttribute("areasServicio", areaServicioService.findAll());
        return "/ticket/ticket-crear";
    }

    @PreAuthorize("hasAuthority('INICIAR_TICKET')")
    @PostMapping(value = "/ticket")
    public String crearTicket(final Locale locale, final Model model, @Valid final TicketDto ticketDto, final BindingResult bindingResult) {
        ticketDto.setEstatusTicketId(1L);
        Optional<Long> optId = SecurityUtils.getCurrentUserId();
        if (optId.isPresent()) {
            ticketDto.setUsuarioCreadorId(optId.get());
        }
        ticketService.save(ticketDto);
        return "redirect:/mis-tickets";
    }

    @PreAuthorize("hasAuthority('INICIAR_TICKET')")
    @GetMapping(value = "/ticket-editar/{id}")
    public String findById(final @PathVariable long id, final Model model, final HttpSession session) {
        model.addAttribute("ticketDto", ticketService.findById(id));
        session.setAttribute("areasServicio", areaServicioService.findAll());
        return "/ticket/ticket-crear";
    }

    @PreAuthorize("hasAuthority('INICIAR_TICKET')")
    @GetMapping(value = "/ticket-detalle/{id}")
    public String buscarInmueblePorId(final @PathVariable long id, final Model model) {
        model.addAttribute("inmuebleDto", ticketService.findById(id));
        return "/ticket/ticket-detalle";
    }

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/ticket-eliminar/{id}")
    public String eliminarTicket(final @PathVariable long id) {
        ticketService.delete(id);
        return "redirect:/tickets";
    }

}

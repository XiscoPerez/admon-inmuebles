package mx.com.admoninmuebles.controller;

import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
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

import mx.com.admoninmuebles.constant.EstatusTicketConst;
import mx.com.admoninmuebles.constant.PrivilegioConst;
import mx.com.admoninmuebles.constant.RolConst;
import mx.com.admoninmuebles.dto.TicketDto;
import mx.com.admoninmuebles.security.SecurityUtils;
import mx.com.admoninmuebles.service.AreaServicioService;
import mx.com.admoninmuebles.service.TicketService;
import mx.com.admoninmuebles.service.UsuarioService;

@Controller
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private AreaServicioService areaServicioService;

    @Autowired
    private UsuarioService usuarioService;

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/tickets")
    public String init(final TicketDto ticketDto, final Model model) {
        model.addAttribute("tickets", ticketService.findAll());
        return "/ticket/tickets";
    }

    @PreAuthorize("hasAnyRole('SOCIO_BI', 'PROVEEDOR')")
    @GetMapping(value = "/mis-tickets")
    public String misTickets(final TicketDto ticketDto, final Model model, final HttpServletRequest request) {
        Optional<Long> optId = SecurityUtils.getCurrentUserId();
        if (optId.isPresent()) {
            if (request.isUserInRole("SOCIO_BI")) {
                model.addAttribute("tickets", ticketService.findByUsuarioCreadorId(optId.get()));
            } else if (request.isUserInRole("PROVEEDOR")) {
                model.addAttribute("tickets", ticketService.findByUsuarioAsignadoId(optId.get()));
            }
        }
        return "/ticket/mis-tickets";
    }

    @PreAuthorize("hasAuthority('ABRIR_TICKET')")
    @GetMapping(value = "/ticket-crear")
    public String ticketCrear(final TicketDto ticketDto, final HttpSession session) {
        session.setAttribute("areasServicio", areaServicioService.findAll());
        return "/ticket/ticket-crear";
    }

    @PreAuthorize("hasAuthority('ABRIR_TICKET')")
    @PostMapping(value = "/ticket")
    public String crearTicket(final Locale locale, @Valid final TicketDto ticketDto, final BindingResult bindingResult) {
        ticketDto.setEstatus(EstatusTicketConst.ABIERTO);
        Optional<Long> optId = SecurityUtils.getCurrentUserId();
        if (optId.isPresent()) {
            ticketDto.setUsuarioCreadorId(optId.get());
        }
        ticketService.save(ticketDto);
        return "redirect:/mis-tickets";
    }

    @PreAuthorize("hasAuthority('ABRIR_TICKET')")
    @GetMapping(value = "/ticket-editar/{id}")
    public String findById(final @PathVariable long id, final Model model, final HttpSession session) {
        model.addAttribute("ticketDto", ticketService.findById(id));
        session.setAttribute("areasServicio", areaServicioService.findAll());
        return "/ticket/ticket-crear";
    }

    @PreAuthorize("hasAuthority('" + PrivilegioConst.VER_TICKET + "')")
    @GetMapping(value = "/ticket-detalle/{id}")
    public String buscarTicketPorId(final @PathVariable long id, final Model model, final HttpServletRequest request, final HttpSession session) {
        String vista;
        TicketDto ticketDto = ticketService.findById(id);
        model.addAttribute("ticketDto", ticketDto);
        if (request.isUserInRole(RolConst.ROLE_SOCIO_BI)) {
            vista = "/ticket/ticket-detalle";
        } else if (request.isUserInRole(RolConst.ROLE_PROVEEDOR)) {
            vista = "/ticket/ticket-aceptar";
        } else {
            vista = "/ticket/ticket-asignar";
            session.setAttribute("proveedores", usuarioService.findByRolesNombreAndAreasServicioId(RolConst.ROLE_PROVEEDOR, ticketDto.getAreaServicioId()));
        }
        return vista;
    }

    @PreAuthorize("hasAuthority('" + PrivilegioConst.ASIGNAR_TICKET + "')")
    @PostMapping(value = "/asignarTicket")
    public String asignar(final Locale locale, @Valid final TicketDto ticketDto, final BindingResult bindingResult) {
        TicketDto ticketDtoTemp = ticketService.findById(ticketDto.getId());
        ticketDtoTemp.setUsuarioAsignadoId(ticketDto.getUsuarioAsignadoId());
        ticketDtoTemp.setEstatus(EstatusTicketConst.ASIGNADO);
        ticketService.save(ticketDtoTemp);
        return "redirect:/tickets";
    }

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/ticket-eliminar/{id}")
    public String eliminarTicket(final @PathVariable long id) {
        ticketService.delete(id);
        return "redirect:/tickets";
    }

}

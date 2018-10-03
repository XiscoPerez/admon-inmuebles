package mx.com.admoninmuebles.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import mx.com.admoninmuebles.dto.ReservacionDto;
import mx.com.admoninmuebles.security.SecurityUtils;
import mx.com.admoninmuebles.service.AreaComunService;
import mx.com.admoninmuebles.service.ReservacionService;

@Controller
public class ReservacionController {
    @Autowired
    private ReservacionService reservacioService;

    @Autowired
    private AreaComunService areaComunService;

    @GetMapping(value = "/reservar-area-comun")
    public String init(final Model model, final HttpSession session, final ReservacionDto reservacionDto) {
        Optional<Long> optUserId = SecurityUtils.getCurrentUserId();
        session.setAttribute("areasComunes", areaComunService.findAll());
        if (optUserId.isPresent()) {
            model.addAttribute("reservaciones", reservacioService.findBySocio(optUserId.get()));
        }
        return "/reservaciones/reservar-area-comun";
    }

    @PostMapping(value = "/buscarReservaciones")
    public String buscarReservaciones(final Model model, final HttpSession session, final ReservacionDto reservacionDto) {
        Optional<Long> optUserId = SecurityUtils.getCurrentUserId();
        if (null != reservacionDto.getAreaComunId()) {
            model.addAttribute("reservaciones", reservacioService.findByAreaComun(reservacionDto.getAreaComunId()));
        } else {
            model.addAttribute("reservaciones", reservacioService.findBySocio(optUserId.get()));
        }
        session.setAttribute("areaComunId", reservacionDto.getAreaComunId());
        return "/reservaciones/reservar-area-comun";
    }

    @PostMapping(value = "/reservarAreaComun")
    public String reservarReservacion(final HttpSession session, final ReservacionDto reservacionDto) {
        reservacionDto.setSocioId(SecurityUtils.getCurrentUserId().get());
        reservacionDto.setAreaComunId((Long) session.getAttribute("areaComunId"));
        reservacioService.save(reservacionDto);
        return "redirect:/reservar-area-comun";
    }

}

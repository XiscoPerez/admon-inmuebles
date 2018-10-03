package mx.com.admoninmuebles.controller;

import java.util.Locale;

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

import mx.com.admoninmuebles.dto.AreaComunDto;
import mx.com.admoninmuebles.service.AreaComunService;
import mx.com.admoninmuebles.service.InmuebleService;

@Controller
public class AreaComunController {
    @Autowired
    private AreaComunService areaComunService;

    @Autowired
    private InmuebleService inmuebleService;

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/areas-comunes")
    public String init(final Model model) {
        model.addAttribute("areasComunes", areaComunService.findAll());
        return "/areacomun/areas-comunes";
    }

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/area-comun-crear")
    public String showForm(final AreaComunDto areaComunDto, final HttpSession session) {
        session.setAttribute("inmuebles", inmuebleService.findAll());
        return "/areacomun/area-comun-crear";
    }

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @PostMapping(value = "/areaComun")
    public String crearAreaComun(final Locale locale, final Model model, @Valid final AreaComunDto areaComunDto, final BindingResult bindingResult) {
        areaComunService.save(areaComunDto);
        return "redirect:/areas-comunes";
    }

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/area-comun-editar/{id}")
    public String findById(final @PathVariable long id, final Model model) {
        model.addAttribute("areaComunDto", areaComunService.findById(id));
        return "/areacomun/area-comun-crear";
    }

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/area-comun-eliminar/{id}")
    public String eliminarAreaComun(final @PathVariable long id) {
        areaComunService.delete(id);
        return "redirect:/areas-comunes";
    }

}

package mx.com.admoninmuebles.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import mx.com.admoninmuebles.dto.ColoniaDto;
import mx.com.admoninmuebles.service.ColoniaService;
import mx.com.admoninmuebles.service.ZonaService;

@Controller
public class ColoniaController {

    @Autowired
    private ColoniaService coloniaService;

    @Autowired
    private ZonaService zonaService;

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/catalogos/colonias")
    public String init(final ColoniaDto coloniaDto, final Model model) {
        model.addAttribute("colonias", coloniaService.findByZonaIsNotNull());
        return "catalogos/colonias";
    }

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/catalogos/colonia-agregar")
    public String agregarColonia(final ColoniaDto coloniaDto, final HttpSession session) {
        session.setAttribute("zonas", zonaService.findAll());
        return "catalogos/colonia-agregar";
    }

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @PostMapping(value = "/catalogos/colonia-agregar", params = { "buscar" })
    public String buscarColonias(@Valid final ColoniaDto coloniaDto, final HttpSession session, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "catalogos/colonia-agregar";
        }
        session.setAttribute("colonias", coloniaService.findBycodigoPostal(coloniaDto.getCodigoPostal()));
        return "catalogos/colonia-agregar";
    }

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @PostMapping(value = "/catalogos/colonia-agregar")
    public String guardar(@Valid final ColoniaDto coloniaDto, final HttpSession session, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "catalogos/colonia-agregar";
        }
        coloniaService.save(coloniaDto);

        session.setAttribute("colonias", coloniaService.findBycodigoPostal(coloniaDto.getCodigoPostal()));
        return "redirect:/catalogos/colonias";
    }

}

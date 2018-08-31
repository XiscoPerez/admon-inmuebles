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
import org.springframework.web.bind.annotation.PostMapping;

import mx.com.admoninmuebles.dto.InmuebleDto;
import mx.com.admoninmuebles.service.BienInmuebleService;
import mx.com.admoninmuebles.service.ColoniaService;
import mx.com.admoninmuebles.service.RolService;
import mx.com.admoninmuebles.storage.StorageService;

@Controller
public class BienInmuebleController {
    @Autowired
    private StorageService storageService;
    @Autowired
    private RolService rolService;
    @Autowired
    private ColoniaService coloniaService;

    @Autowired
    private BienInmuebleService bienInmuebleService;

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/inmuebles")
    public String init(final Model model) {
        model.addAttribute("inmuebles", bienInmuebleService.findAll());
        return "inmuebles/inmuebles";
    }

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/inmueble-crear")
    public String crearInmueble(final InmuebleDto inmuebleDto, final HttpSession session) {
        session.setAttribute("colonias", coloniaService.findByZonaIsNotNull());
        session.setAttribute("usuariosAdminBi", rolService.findUsuariosByNombreRol("ROLE_ADMIN_BI"));
        return "inmuebles/inmueble-crear";
    }

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @PostMapping(value = "/inmueble-crear")
    public String guardarInmueble(final Locale locale, final Model model, @Valid final InmuebleDto inmuebleDto, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/inmuebles/inmueble-crear";
        }
        inmuebleDto.setImagenUrl("/" + storageService.store(inmuebleDto.getImagen()));
        bienInmuebleService.save(inmuebleDto);

        return "redirect:inmuebles";
    }

}

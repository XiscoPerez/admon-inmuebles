package mx.com.admoninmuebles.controller;

import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import mx.com.admoninmuebles.dto.ProveedorDto;
import mx.com.admoninmuebles.dto.SocioDto;
import mx.com.admoninmuebles.dto.UsuarioDto;
import mx.com.admoninmuebles.listener.event.OnRegistroCompletoEvent;
import mx.com.admoninmuebles.service.AreaServicioService;
import mx.com.admoninmuebles.service.ProveedorService;
import mx.com.admoninmuebles.service.SocioService;
@Controller
public class SociosController {
	
	Logger logger = LoggerFactory.getLogger(SociosController.class);

	@Autowired
	private SocioService socioService;
	
	@Autowired
	private AreaServicioService areaServicioService;
	
    @Autowired
    private ApplicationEventPublisher eventPublisher;

	@PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
	@GetMapping(value = "/socios")
	public String init(final Model model) {
		model.addAttribute("socios", socioService.getSocios());
		return "socios/socios";
	}
	
	@PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/socio-crear")
    public String crearProveedorInit(final ProveedorDto socioDto, final Model model) {
		 model.addAttribute("areasServicio", areaServicioService.findAll());
        return "socios/socio-crear";
    }

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @PostMapping(value = "/socio-crear")
    public String crearProveedor(final HttpServletRequest request, final Locale locale, final Model model, @Valid final SocioDto socioDto, final BindingResult bindingResult) {
    	logger.info(socioDto.toString());
        if (bindingResult.hasErrors()) {
            return "socios/socio-crear";
        }
        
        SocioDto socioNuevo = socioService.guardar(socioDto);
        eventPublisher.publishEvent(new OnRegistroCompletoEvent(socioNuevo, request.getLocale(), getAppUrl(request)));
        return "redirect:/socios";
    }

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/socio-detalle/{id}")
    public String buscarProveedorPorId(final @PathVariable long id, final Model model) {
    	SocioDto socioDto = socioService.buscarSocioPorId(id);
        model.addAttribute("socioDto", socioService.buscarSocioPorId(id));
//        socioDto.setAreasServicioSeleccionados(socioDto.getAreasServicio().stream().map(as -> as.getId()).collect(Collectors.toList()));
//        
//        model.addAttribute("comentariosDto", socioDto.getComentarios());
        model.addAttribute("socioDto", socioDto);
//        model.addAttribute("areasServicio", socioDto.getAreasServicio());
        return "socios/socio-detalle";
    }

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/socio-editar/{id}")
    public String editarProveedor(final @PathVariable long id, final Model model, final HttpSession session) {
    	SocioDto socioDto = socioService.buscarSocioPorId(id);
//    	socioDto.setAreasServicioSeleccionados(socioDto.getAreasServicio().stream().map(as -> as.getId()).collect(Collectors.toList()));
        model.addAttribute("socioDto", socioDto);
//        model.addAttribute("areasServicio", areaServicioService.findAll());
//        model.addAttribute("coloniaDto", socioDto);
        return "socios/socio-editar";
    }

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @PostMapping(value = "/socio-editar")
    public String editarInmueble(final Locale locale, final Model model, @Valid final SocioDto socioDto, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "socios/socio-editar";
        }

        socioService.guardar(socioDto);
        return "redirect:/socios";
    }

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/socio-eliminar/{id}")
    public String eliminarInmueble(final @PathVariable Long id) {
    	socioService.eliminar(id);
        return "redirect:/socios";
    }
    
    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}

package mx.com.admoninmuebles.controller;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
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

import mx.com.admoninmuebles.constant.RolConst;
import mx.com.admoninmuebles.dto.ColoniaDto;
import mx.com.admoninmuebles.dto.UsuarioDto;
import mx.com.admoninmuebles.dto.ZonaDto;
import mx.com.admoninmuebles.listener.event.OnRegistroCompletoEvent;
import mx.com.admoninmuebles.security.SecurityUtils;
import mx.com.admoninmuebles.service.SocioService;
import mx.com.admoninmuebles.service.ColoniaService;
import mx.com.admoninmuebles.service.InmuebleService;
import mx.com.admoninmuebles.service.RolService;
import mx.com.admoninmuebles.service.UsuarioService;
import mx.com.admoninmuebles.service.ZonaService;
@Controller
public class SocioController {
	
	Logger logger = LoggerFactory.getLogger(SocioController.class);
	
    @Autowired
    private RolService rolService;

	@Autowired
	private SocioService socioService;
	
	@Autowired
	private ZonaService zonaService;
	
	@Autowired
	private ColoniaService coloniaService;
	
	@Autowired
	private InmuebleService inmuebleService;
	
	@Autowired
	private UsuarioService usuarioService;
	
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
    public String crearSocioInit(final UsuarioDto usuarioDto, final Model model, final HttpServletRequest request) {
		model.addAttribute("rolesDto", rolService.getRolesSociosRepresentantes());
		Optional<Long> optId = SecurityUtils.getCurrentUserId();
        if (optId.isPresent()) {
            if (request.isUserInRole(RolConst.ROLE_ADMIN_CORP)) {
                model.addAttribute("zonasDto", zonaService.findAll());
                
            } else if (request.isUserInRole(RolConst.ROLE_ADMIN_ZONA)) {
            	model.addAttribute("zonasDto", zonaService.findByAdminZonaId(optId.get()));
            
            } else if (request.isUserInRole(RolConst.ROLE_ADMIN_BI)) {
            	model.addAttribute("inmueblesDto", inmuebleService.findByAdminBiId(optId.get()));
            }
        }
        return "socios/socio-crear";
    }

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @PostMapping(value = "/socio-crear")
    public String crearSocio(final HttpServletRequest request, final Locale locale, final Model model, @Valid final UsuarioDto usuarioDto, final BindingResult bindingResult) {
    	logger.info(usuarioDto.toString());
        if (bindingResult.hasErrors()) {
            return "socios/socio-crear";
        }
        
        UsuarioDto socioNuevo = (UsuarioDto) usuarioService.crearCuenta(usuarioDto);
        eventPublisher.publishEvent(new OnRegistroCompletoEvent(socioNuevo, request.getLocale(), getAppUrl(request)));
        return "redirect:/socios";
    }

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/socio-detalle/{id}")
    public String buscarsocioPorId(final @PathVariable long id, final Model model) {
    	UsuarioDto usuarioDto = usuarioService.findById(id);
        model.addAttribute("usuarioDto", socioService.buscarSocioPorId(id));
        model.addAttribute("usuarioDto", usuarioDto);
        return "socios/socio-detalle";
    }

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/socio-editar/{id}")
    public String editarSocio(final @PathVariable long id, final Model model, final HttpServletRequest request) {
    	UsuarioDto usuarioDto = usuarioService.findById(id);
    	List<Long> rolesUsuario = usuarioDto.getRoles().stream().map(rol -> rol.getId()).collect(Collectors.toList());
    	usuarioDto.setRolSeleccionado( rolesUsuario.get(0) );
        model.addAttribute("usuarioDto", usuarioDto);
        model.addAttribute("rolesDto", rolService.getRolesSociosRepresentantes());
        
        Optional<Long> optId = SecurityUtils.getCurrentUserId();
        if (optId.isPresent()) {
            if (request.isUserInRole(RolConst.ROLE_ADMIN_CORP) || request.isUserInRole(RolConst.ROLE_ADMIN_ZONA)) {
            	Collection<ZonaDto> zonasDto = request.isUserInRole(RolConst.ROLE_ADMIN_CORP) ? zonaService.findAll() : zonaService.findByAdminZonaId(optId.get());
                model.addAttribute("zonasDto", zonasDto);
           		model.addAttribute("coloniasDto", coloniaService.findByZonaCodigo(usuarioDto.getInmuebleDireccionAsentamientoZonaCodigo()));
       			model.addAttribute("inmueblesDto", inmuebleService.findByDireccionAsentamientoId(usuarioDto.getInmuebleDireccionAsentamientoId()));
            } else if (request.isUserInRole(RolConst.ROLE_ADMIN_BI)) {
            	model.addAttribute("inmueblesDto", inmuebleService.findByAdminBiId(optId.get()));
            }
        }
        
        logger.info(usuarioDto.toString());
        return "socios/socio-editar";
    }

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @PostMapping(value = "/socio-editar")
    public String editarInmueble(final Locale locale, final Model model, @Valid final UsuarioDto usuarioDto, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "socios/socio-editar";
        }

        usuarioService.crearCuenta(usuarioDto);
        return "redirect:/socios";
    }

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/socio-eliminar/{id}")
    public String eliminarInmueble(final @PathVariable Long id) {
    	usuarioService.deleteById(id);
        return "redirect:/socios";
    }
    
    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}

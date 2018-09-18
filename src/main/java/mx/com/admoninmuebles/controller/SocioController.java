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
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import mx.com.admoninmuebles.constant.RolConst;
import mx.com.admoninmuebles.dto.ColoniaDto;
import mx.com.admoninmuebles.dto.InmuebleDto;
import mx.com.admoninmuebles.dto.UsuarioDto;
import mx.com.admoninmuebles.dto.ZonaDto;
import mx.com.admoninmuebles.error.BusinessException;
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
    private MessageSource messages;
	
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
    
    @PreAuthorize("hasAnyRole('SOCIO_BI')")
    @GetMapping(value = "/sociobi/inicio")
    public String inicioSocioBi(final Model model) {
    	Long socioBiLogueadoId = SecurityUtils.getCurrentUserId().get();
    	UsuarioDto usuarioDto = usuarioService.findById(socioBiLogueadoId);
    	model.addAttribute("socioDto", usuarioDto);
        model.addAttribute("inmuebleDto", inmuebleService.findById(usuarioDto.getInmuebleId()));
        return "sociobi/inicio";
    }
    
    @PreAuthorize("hasAnyRole('REP_BI')")
    @GetMapping(value = "/repbi/inicio")
    public String inicioRepBi(final Model model) {
    	Long socioBiLogueadoId = SecurityUtils.getCurrentUserId().get();
    	UsuarioDto usuarioDto = usuarioService.findById(socioBiLogueadoId);
    	logger.info("REP NI::::: " + usuarioDto == null ? "NADA" : usuarioDto.toString());
    	
    	InmuebleDto inmuebleDto = inmuebleService.findById(usuarioDto.getInmuebleId());
    	
    	model.addAttribute("repDto", usuarioDto);
        model.addAttribute("inmuebleDto", inmuebleDto);
        model.addAttribute("socios", inmuebleService.findSociosByInmuebleId(inmuebleDto.getId()));
        model.addAttribute("tickets", inmuebleService.findTicketsByInmuebleId(inmuebleDto.getId()));
        return "repbi/inicio";
    }

	@PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
	@GetMapping(value = "/socios")
	public String init(final Model model) {
		model.addAttribute("socios", socioService.getSocios());
		return "socios/socios";
	}
	
	@PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/socio-crear")
    public String crearSocioInit(final UsuarioDto usuarioDto, final Model model, final HttpServletRequest request, final HttpSession session) {
		session.setAttribute("rolesDto", rolService.getRolesSociosRepresentantes());
		Optional<Long> optId = SecurityUtils.getCurrentUserId();
        if (optId.isPresent()) {
            if (request.isUserInRole(RolConst.ROLE_ADMIN_CORP)) {
                session.setAttribute("zonasDto", zonaService.findAll());
                
            } else if (request.isUserInRole(RolConst.ROLE_ADMIN_ZONA)) {
            	session.setAttribute("zonasDto", zonaService.findByAdminZonaId(optId.get()));
            
            } else if (request.isUserInRole(RolConst.ROLE_ADMIN_BI)) {
            	session.setAttribute("inmueblesDto", inmuebleService.findByAdminBiId(optId.get()));
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
        
        try {
	        UsuarioDto socioNuevo = (UsuarioDto) usuarioService.crearCuenta(usuarioDto);
	        eventPublisher.publishEvent(new OnRegistroCompletoEvent(socioNuevo, request.getLocale(), getAppUrl(request)));
	        return "redirect:/socios";
        }catch(BusinessException e) {
   		 bindingResult.addError(new ObjectError(messages.getMessage(e.getMessage(), null, locale), messages.getMessage(e.getMessage(), null, locale)));
   		 return "socios/socio-crear";
   	 	}
    }

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/socio-detalle/{id}")
    public String buscarsocioPorId(final @PathVariable long id, final Model model) {
        model.addAttribute("usuarioDto", socioService.buscarSocioPorId(id));
        return "socios/socio-detalle";
    }

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/socio-editar/{id}")
    public String editarSocio(final @PathVariable long id, final Model model, final HttpServletRequest request, final HttpSession session) {
    	UsuarioDto usuarioDto = usuarioService.findById(id);
    	List<Long> rolesUsuario = usuarioDto.getRoles().stream().map(rol -> rol.getId()).collect(Collectors.toList());
    	usuarioDto.setRolSeleccionado( rolesUsuario.get(0) );
        model.addAttribute("usuarioDto", usuarioDto);
        session.setAttribute("rolesDto", rolService.getRolesSociosRepresentantes());
        
        Optional<Long> optId = SecurityUtils.getCurrentUserId();
        if (optId.isPresent()) {
            if (request.isUserInRole(RolConst.ROLE_ADMIN_CORP) || request.isUserInRole(RolConst.ROLE_ADMIN_ZONA)) {
            	Collection<ZonaDto> zonasDto = request.isUserInRole(RolConst.ROLE_ADMIN_CORP) ? zonaService.findAll() : zonaService.findByAdminZonaId(optId.get());
            	session.setAttribute("zonasDto", zonasDto);
            	session.setAttribute("coloniasDto", coloniaService.findByZonaCodigo(usuarioDto.getInmuebleDireccionAsentamientoZonaCodigo()));
            	session.setAttribute("inmueblesDto", inmuebleService.findByDireccionAsentamientoId(usuarioDto.getInmuebleDireccionAsentamientoId()));
            } else if (request.isUserInRole(RolConst.ROLE_ADMIN_BI)) {
            	session.setAttribute("inmueblesDto", inmuebleService.findByAdminBiId(optId.get()));
            }
        }
        
        logger.info(usuarioDto.toString());
        return "socios/socio-editar";
    }

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @PostMapping(value = "/socio-editar")
    public String editarSocio(final Locale locale, final Model model, @Valid final UsuarioDto usuarioDto, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "socios/socio-editar";
        }

        usuarioService.editarCuenta(usuarioDto);
        return "redirect:/socios";
    }

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/socio-eliminar/{id}")
    public String eliminarSocio(final @PathVariable Long id) {
    	usuarioService.deleteById(id);
        return "redirect:/socios";
    }
    
    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}

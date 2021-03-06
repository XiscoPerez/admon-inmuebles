package mx.com.admoninmuebles.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.mapping.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.com.admoninmuebles.dto.CambioContraseniaDto;
import mx.com.admoninmuebles.dto.RecuperaContraseniaDto;
import mx.com.admoninmuebles.dto.RecuperacionContraseniaCorreoDto;
import mx.com.admoninmuebles.dto.RolDto;
import mx.com.admoninmuebles.dto.UsuarioDto;
import mx.com.admoninmuebles.dto.ZonaDto;
import mx.com.admoninmuebles.error.BusinessException;
import mx.com.admoninmuebles.constant.RolConst;
import mx.com.admoninmuebles.dto.ActivacionUsuarioDto;
import mx.com.admoninmuebles.listener.event.OnRecuperacionContraseniaEvent;
import mx.com.admoninmuebles.listener.event.OnRegistroCompletoEvent;
import mx.com.admoninmuebles.security.SecurityUtils;
import mx.com.admoninmuebles.service.RolService;
import mx.com.admoninmuebles.service.UsuarioService;
import mx.com.admoninmuebles.service.ZonaService;
import mx.com.admoninmuebles.storage.StorageService;
import mx.com.admoninmuebles.service.ActivacionUsuarioService;
import mx.com.admoninmuebles.service.ColoniaService;
import mx.com.admoninmuebles.service.InmuebleService;
import mx.com.admoninmuebles.service.RecuperacionContraseniaService;

@Controller
public class UsuarioController {
	
	Logger logger = LoggerFactory.getLogger(UsuarioController.class);
	
    @Autowired
    private StorageService storageService;

    @Autowired
    private MessageSource messages;

    @Autowired
    private UsuarioService userService;
    
    @Autowired
    private RolService rolService;
    
    @Autowired
    private ActivacionUsuarioService activacionUsuarioService;
    
    @Autowired
    private RecuperacionContraseniaService recuperacionContraseniaService;
    
	@Autowired
	private InmuebleService inmuebleService;
	
	@Autowired
	private ColoniaService coloniaService;
	
	@Autowired
	private ZonaService zonaService;
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping(value = "/crearUsuario")
    public String showForm(final UsuarioDto userDto) {
        return "crearUsuario";
    }
    

    @PreAuthorize("hasAnyRole('ADMIN_CORP')")
    @GetMapping(value = "/admincorp")
    public String initAdminCorp(final UsuarioDto usuarioDto, final Model model) {
    	model.addAttribute("zonas", zonaService.findAll());
        model.addAttribute("colonias", coloniaService.findByZonaIsNotNull());
        model.addAttribute("inmuebles", inmuebleService.findAll());
//        model.addAttribute("usuarios", userService.findAll());
        return "admincorp/inicio";
    }
    
    @PreAuthorize("hasAnyRole('ADMIN_ZONA')")
    @GetMapping(value = "/adminzona")
    public String initAdminZona(final UsuarioDto usuarioDto, final Model model) {
    	Long adminZonaLogueadoId = SecurityUtils.getCurrentUserId().get();
    	ZonaDto zona = zonaService.findByAdminZonaId(adminZonaLogueadoId).stream().findFirst().get();
//        model.addAttribute("usuarios", userService.findAll());
        model.addAttribute("colonias", coloniaService.findByZonaCodigo( zona.getCodigo() ));
        model.addAttribute("inmuebles", inmuebleService.findAll());
        return "adminzona/inicio";
    }
    
    @PreAuthorize("hasAnyRole('ADMIN_BI')")
    @GetMapping(value = "/adminbi")
    public String initAdminBi(final UsuarioDto usuarioDto, final Model model) {
    	Long adminBiLogueadoId = SecurityUtils.getCurrentUserId().get();
        model.addAttribute("inmuebles", inmuebleService.findByAdminBiId(adminBiLogueadoId));
        return "adminbi/inicio";
    }

    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/usuarios")
    public String init(final UsuarioDto usuarioDto, final Model model, final HttpServletRequest request) {
    	
    	if (request.isUserInRole(RolConst.ROLE_ADMIN_CORP)) {
    		model.addAttribute("usuarios", userService.findAllAdministradores());
    	} else if(request.isUserInRole(RolConst.ROLE_ADMIN_ZONA)){
    		Long adminZonaLogueadoId = SecurityUtils.getCurrentUserId().get();
        	ZonaDto zona = zonaService.findByAdminZonaId(adminZonaLogueadoId).stream().findFirst().get();
    		model.addAttribute("usuarios", userService.findAdministradoresBiByZonaCodigo(zona.getCodigo()));
    	}else {
    		model.addAttribute("usuarios", Collections.EMPTY_LIST);
    	}
        return "usuarios/usuarios";
    }
    
    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/usuarios/nuevo")
    public String nuevoInit(final UsuarioDto usuarioDto, final Model model, final HttpSession session, final HttpServletRequest request) {
    	
    	if (request.isUserInRole(RolConst.ROLE_ADMIN_CORP)) {
    		session.setAttribute("rolesDto", rolService.getRolesAdministradores());
    	}  else if(request.isUserInRole(RolConst.ROLE_ADMIN_ZONA)){
    		List<String> nombres = new ArrayList<>();
    		nombres.add(  RolConst.ROLE_ADMIN_BI );
    		session.setAttribute("rolesDto", rolService.findByNombres( nombres ));
    	} else {
    		session.setAttribute("rolesDto", Collections.emptyList());
    	}
    	
        return "usuarios/usuario-crear";
    }
    
    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @PostMapping(value = "/usuarios")
    public String guardar(final HttpServletRequest request, final Locale locale, final Model model, @Valid final UsuarioDto usuarioDto, final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
    	logger.info(usuarioDto.toString());
    	 if (bindingResult.hasErrors()) {
             return "usuarios/usuario-crear";
         }
    	
    	 try {
    		 UsuarioDto newUsuarioDto = userService.crearCuenta(usuarioDto);
    		 eventPublisher.publishEvent(new OnRegistroCompletoEvent(newUsuarioDto, request.getLocale(), getAppUrl(request)));
    		 return "redirect:/usuarios";
    	 }catch(BusinessException e) {
    		 bindingResult.addError(new ObjectError(messages.getMessage(e.getMessage(), null, locale), messages.getMessage(e.getMessage(), null, locale)));
    		 return "usuarios/usuario-crear";
    	 }
    }
    
    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/usuarios/editar/{idUsuario}")
    public String edicionInit(final @PathVariable Long idUsuario, final Model model, final HttpSession session, final HttpServletRequest request) {
    	UsuarioDto usuarioDto = userService.findById(idUsuario);
    	List<Long> rolesUsuario = usuarioDto.getRoles().stream().map(rol -> rol.getId()).collect(Collectors.toList());
    	usuarioDto.setRolSeleccionado( rolesUsuario.get(0) );
    	model.addAttribute("usuarioDto", usuarioDto);
    	
    	if (request.isUserInRole(RolConst.ROLE_ADMIN_CORP)) {
//    		session.setAttribute("rolesDto", rolService.getRolesAdministradores());
    		model.addAttribute("rolesDto", rolService.getRolesAdministradores());
    	}  else if(request.isUserInRole(RolConst.ROLE_ADMIN_ZONA)){
    		List<String> nombres = new ArrayList<>();
    		nombres.add(  RolConst.ROLE_ADMIN_BI );
//    		session.setAttribute("rolesDto", rolService.findByNombres( nombres ));
    		model.addAttribute("rolesDto", rolService.findByNombres( nombres ));
    	} else {
//    		session.setAttribute("rolesDto", Collections.emptyList());
    		model.addAttribute("rolesDto", Collections.emptyList());
    	}
    	
        return "usuarios/usuario-editar";
    }
    
    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @PostMapping(value = "/usuarios/editar")
    public String editar(final Locale locale, final Model model, @Valid final UsuarioDto usuarioDto, final BindingResult bindingResult) {
    	System.out.println("Editando usuario " + usuarioDto.toString());
    	if (bindingResult.hasErrors()) {
    		System.out.println("Errores " + bindingResult.toString() );
            return "usuarios/usuario-editar";
        }
//    	usuarioDto.setFotoUrl("/" + storageService.store(usuarioDto.getImagen()));
    	userService.editarCuenta(usuarioDto);
    	return "redirect:/usuarios";
    }
    
    @PreAuthorize("hasRole('ADMIN_CORP')")
    @GetMapping(value = "/usuarios/eliminar/{idUsuario}")
    public String eliminar(final @PathVariable Long idUsuario, final Model model) {
    	Long adminCorpLogueadoId = SecurityUtils.getCurrentUserId().get();
    	
    	if(idUsuario != null && adminCorpLogueadoId != idUsuario ) {
    		try {
    			userService.deleteById(idUsuario);
    		} catch (BusinessException be) {
    			 return "redirect:/usuarios";
    		}
    	}
        return "redirect:/usuarios";
    }
    
    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/usuarios/ver/{idUsuario}")
    public String ver(final @PathVariable Long idUsuario, final Model model) {
    	UsuarioDto usuarioDto = userService.findById(idUsuario);
    	List<Long> rolesUsuario = usuarioDto.getRoles().stream().map(rol -> rol.getId()).collect(Collectors.toList());
    	usuarioDto.setRolSeleccionado( rolesUsuario.get(0) );
    	model.addAttribute("usuarioDto", usuarioDto);
        return "usuarios/usuario-ver";
    }
    
    @GetMapping(value = "/usuarios/perfil")
    public String verMiPerfil(final Model model) {
    	UsuarioDto usuarioDto = userService.findUserLogin();
    	List<Long> rolesUsuario = usuarioDto.getRoles().stream().map(rol -> rol.getId()).collect(Collectors.toList());
    	usuarioDto.setRolSeleccionado( rolesUsuario.get(0) );
    	model.addAttribute("usuarioDto", usuarioDto);
    	model.addAttribute("cambioContraseniaDto", new CambioContraseniaDto());
    	model.addAttribute("rolesDto", rolService.findAll());
        return "usuarios/usuario-perfil";
    }
    
    @PostMapping(value = "/usuarios/perfil/editar")
    public String editarPerfil(final Locale locale, final Model model, @Valid final UsuarioDto usuarioDto, final BindingResult bindingResult) {
    	System.out.println("Editando Perfil " + usuarioDto.toString());
    	model.addAttribute("cambioContraseniaDto", new CambioContraseniaDto());
    	if (bindingResult.hasErrors()) {
            return "usuarios/usuario-perfil";
        }
    	if( usuarioDto.getImagen() != null && !usuarioDto.getImagen().isEmpty() && !StringUtils.cleanPath(usuarioDto.getImagen().getOriginalFilename()).contains("..")) {
    		usuarioDto.setFotoUrl("/" + storageService.store(usuarioDto.getImagen()));
    	}
    	userService.editarPerfil(usuarioDto);
    	return "redirect:/usuarios/perfil";
    }
    
    @PostMapping(value = "/usuarios/perfil/cambioContrasenia")
    public String cambiarContrasenia(final Locale locale, final Model model, @Valid final CambioContraseniaDto cambioContraseniaDto, final BindingResult bindingResult) {
    	
    	userService.cambiarContrasenia(cambioContraseniaDto);
    	
    	return "redirect:/usuarios/perfil/";
    }
    
    
    @GetMapping(value = "/usuarios/activar/{token}")
    public String activarcionUsuarioInit(final HttpServletRequest request, final Model model, @PathVariable final String token) {
    	if(!activacionUsuarioService.isTokenValido(token)){
    		return "usuarios/usuario-token-novalido";
    	}
    	ActivacionUsuarioDto activacionUsuarioDto = new ActivacionUsuarioDto();
    	activacionUsuarioDto.setToken(token);
    	model.addAttribute("token", token);
    	model.addAttribute("activacionUsuarioDto", activacionUsuarioDto);
        return "usuarios/usuario-activar";
    }
    
    @PostMapping(value = "/usuarios/activar")
    public String activarUsuario(final Locale locale, final Model model, @Valid final ActivacionUsuarioDto activacionUsuarioDto, final BindingResult bindingResult) {
    	if (bindingResult.hasErrors()) {
            return "usuarios/usuario-activar";
        }
    	UsuarioDto usuarioDto = activacionUsuarioService.activar(activacionUsuarioDto);
        return "redirect:/login";
    }
    
    
    @PostMapping(value = "/usuarios/correo-recuperar-contrasenia")
    public String enviarCorreoRecuperacionContrasenia(final HttpServletRequest request, final Locale locale, final Model model, @Valid final RecuperacionContraseniaCorreoDto recuperacionContraseniaCorreo, final BindingResult bindingResult) {
    	UsuarioDto usuarioDto = userService.findByUsernameOrCorreo(recuperacionContraseniaCorreo.getLogin());
    	eventPublisher.publishEvent(new OnRecuperacionContraseniaEvent(usuarioDto, request.getLocale(), getAppUrl(request)));
        return "redirect:/login";
    }
    
    @GetMapping(value = "/usuarios/recuperar-contrasenia/{token}")
    public String recuperarContraseniaInit(final HttpServletRequest request, final Model model, @PathVariable final String token) {
    	if(!recuperacionContraseniaService.isTokenValido(token)){
    		return "usuarios/usuario-token-novalido";
    	}
    	RecuperaContraseniaDto recuperaContraseniaDto = new RecuperaContraseniaDto();
    	recuperaContraseniaDto.setToken(token);
    	model.addAttribute("token", token);
    	model.addAttribute("recuperaContraseniaDto", recuperaContraseniaDto);
        return "usuarios/usuario-recupera-contrasenia";
    }
    
    @PostMapping(value = "/usuarios/recuperar-contrasenia/")
    public String recuperarContrasenia(final HttpServletRequest request, final Locale locale, final Model model, @Valid final RecuperaContraseniaDto recuperaContraseniaDto, final BindingResult bindingResult) {
    	if (bindingResult.hasErrors()) {
            return "usuarios/usuario-recupera-contrasenia";
        }
    	recuperacionContraseniaService.guardarNuevaContrasenia(recuperaContraseniaDto);
        return "redirect:/login";
    }
    
    @GetMapping(value = "/usuarios/recuperar-contrasenia-peticion")
    public String recuperarContraseniaPticionInit(final Model model) {
    	model.addAttribute("recuperacionContraseniaCorreoDto", new RecuperacionContraseniaCorreoDto());
        return "usuarios/usuario-recupera-contrasenia-peticion";
    }
    
    
    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}

package mx.com.admoninmuebles.controller;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mx.com.admoninmuebles.dto.CambioContraseniaDto;
import mx.com.admoninmuebles.dto.RecuperaContraseniaDto;
import mx.com.admoninmuebles.dto.RecuperacionContraseniaCorreoDto;
import mx.com.admoninmuebles.dto.UsuarioDto;
import mx.com.admoninmuebles.dto.ActivacionUsuarioDto;
import mx.com.admoninmuebles.dto.ZonaDto;
import mx.com.admoninmuebles.listener.event.OnRecuperacionContraseniaEvent;
import mx.com.admoninmuebles.listener.event.OnRegistroCompletoEvent;
import mx.com.admoninmuebles.service.RolService;
import mx.com.admoninmuebles.service.UsuarioService;
import mx.com.admoninmuebles.service.ActivacionUsuarioService;
import mx.com.admoninmuebles.service.RecuperacionContraseniaService;

@Controller
public class UsuarioController {

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
    private ApplicationEventPublisher eventPublisher;

    @GetMapping(value = "/crearUsuario")
    public String showForm(final UsuarioDto userDto) {
        return "crearUsuario";
    }

    @PreAuthorize("hasRole('ADMIN_CORP')")
    @GetMapping(value = "/usuarios")
    public String init(final UsuarioDto usuarioDto, final Model model) {
        model.addAttribute("usuarios", userService.findAll());
        return "usuarios/usuarios";
    }
    
    @PreAuthorize("hasRole('ADMIN_CORP')")
    @GetMapping(value = "/usuarios/nuevo")
    public String nuevoInit(final UsuarioDto usuarioDto, final Model model) {
    	model.addAttribute("rolesDto", rolService.findAll());
        return "usuarios/usuario-crear";
    }
    
    @PreAuthorize("hasRole('ADMIN_CORP')")
    @PostMapping(value = "/usuarios")
    public String guardar(final HttpServletRequest request, final Locale locale, final Model model, @Valid final UsuarioDto usuarioDto, final BindingResult bindingResult) {
    	 if (bindingResult.hasErrors()) {
             return "usuarios/usuario-crear";
         }
    	UsuarioDto newUsuarioDto = userService.crearCuenta(usuarioDto);
    	eventPublisher.publishEvent(new OnRegistroCompletoEvent(newUsuarioDto, request.getLocale(), getAppUrl(request)));
    	return "redirect:/usuarios";
    }
    
    @PreAuthorize("hasRole('ADMIN_CORP')")
    @GetMapping(value = "/usuarios/editar/{idUsuario}")
    public String edicionInit(final @PathVariable Long idUsuario, final Model model) {
    	UsuarioDto usuarioDto = userService.findById(idUsuario);
    	List<Long> rolesUsuario = usuarioDto.getRoles().stream().map(rol -> rol.getId()).collect(Collectors.toList());
    	usuarioDto.setRolSeleccionado( rolesUsuario.get(0) );
    	model.addAttribute("usuarioDto", usuarioDto);
    	model.addAttribute("rolesDto", rolService.findAll());
        return "usuarios/usuario-editar";
    }
    
    @PreAuthorize("hasRole('ADMIN_CORP')")
    @PostMapping(value = "/usuarios/editar")
    public String editar(final Locale locale, final Model model, @Valid final UsuarioDto usuarioDto, final BindingResult bindingResult) {
    	System.out.println("USUARIO: " + usuarioDto.toString());
    	userService.crearCuenta(usuarioDto);
    	return "redirect:/usuarios";
    }
    
    @PreAuthorize("hasRole('ADMIN_CORP')")
    @GetMapping(value = "/usuarios/eliminar/{idUsuario}")
    public String eliminar(final @PathVariable Long idUsuario, final Model model) {
    	userService.deleteById(idUsuario);
        return "redirect:/usuarios";
    }
//    
    @PreAuthorize("hasRole('ADMIN_CORP')")
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
    
    @PreAuthorize("hasRole('ADMIN_CORP')")
    @PostMapping(value = "/usuarios/perfil/editar")
    public String editarPerfil(final Locale locale, final Model model, @Valid final UsuarioDto usuarioDto, final BindingResult bindingResult) {
    	if (bindingResult.hasErrors()) {
            return "usuarios/usuario-perfil";
        }
    	userService.editarPerfil(usuarioDto);
    	return "redirect:/usuarios/perfil/";
    }
    
    @PostMapping(value = "/usuarios/perfil/cambioContrasenia")
    public String cambiarContrasenia(final Locale locale, final Model model, @Valid final CambioContraseniaDto cambioContraseniaDto, final BindingResult bindingResult) {
    	
    	userService.cambiarContrasenia(cambioContraseniaDto);
    	
    	return "redirect:/usuarios/perfil/";
    }
    
    
    @GetMapping(value = "/usuarios/activar/{token}")
    public String activarcionUsuarioInit(final HttpServletRequest request, final Model model, @PathVariable final String token) {
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
    
//    @GetMapping(value = "/usuarios/correo-recuperar-contrasenia")
//    public String enviarCorreoRecuperacionContrasenia(final RecuperacionContraseniaCorreo recuperacionContraseniaCorreo) {
//        return "redirect:/login";
//    }
    
    @PostMapping(value = "/usuarios/correo-recuperar-contrasenia")
    public String enviarCorreoRecuperacionContrasenia(final HttpServletRequest request, final Locale locale, final Model model, @Valid final RecuperacionContraseniaCorreoDto recuperacionContraseniaCorreo, final BindingResult bindingResult) {
    	System.out.println("Enviando correo de recuperación de contraseña");
    	System.out.println("LOGIN: " + recuperacionContraseniaCorreo.getLogin());
    	UsuarioDto usuarioDto = userService.findByUsernameOrCorreo(recuperacionContraseniaCorreo.getLogin());
    	System.out.println("Usuario: " + usuarioDto.getUsername());
    	eventPublisher.publishEvent(new OnRecuperacionContraseniaEvent(usuarioDto, request.getLocale(), getAppUrl(request)));
        return "redirect:/login";
    }
    
    @GetMapping(value = "/usuarios/recuperar-contrasenia/{token}")
    public String recuperarContraseniaInit(final HttpServletRequest request, final Model model, @PathVariable final String token) {
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

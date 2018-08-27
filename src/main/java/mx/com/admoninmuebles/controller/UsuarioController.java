package mx.com.admoninmuebles.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import mx.com.admoninmuebles.dto.UsuarioDto;
import mx.com.admoninmuebles.dto.ZonaDto;
import mx.com.admoninmuebles.service.RolService;
import mx.com.admoninmuebles.service.UsuarioService;

@Controller
public class UsuarioController {

    @Autowired
    private MessageSource messages;

    @Autowired
    private UsuarioService userService;
    
    @Autowired
    private RolService rolService;

    @GetMapping(value = "/crearUsuario")
    public String showForm(final UsuarioDto userDto) {
        return "crearUsuario";
    }

//    @PostMapping(value = "/crearUsuario")
//    public String crearUsuario(final Locale locale, final Model model, @Valid final UsuarioDto usuarioDto, final BindingResult bindingResult) {
//        FieldError error;
//
//        if (!usuarioDto.getContrasenia()().equals(usuarioDto.getConfirmPassword())) {
//            error = new FieldError("usuarioDto", "confirmPassword", messages.getMessage("message.password.notMatch", null, locale));
//            bindingResult.addError(error);
//            error = new FieldError("usuarioDto", "password", messages.getMessage("message.password.notMatch", null, locale));
//            bindingResult.addError(error);
//        } else {
//            userService.save(usuarioDto);
//        }
//
//        if (bindingResult.hasErrors()) {
//            return "crearUsuario";
//        }
//
//        model.addAttribute("message", messages.getMessage("message.success.crearUsuario", null, locale));
//        return "redirect:/login";
//    }
//    
    
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
    public String guardar(final Locale locale, final Model model, @Valid final UsuarioDto usuarioDto, final BindingResult bindingResult) {
    	System.out.println("USUARIO: " + usuarioDto.toString());
    	System.out.println("ROLES: " + usuarioDto.getRolesSeleccionados().size());
    	userService.crearCuenta(usuarioDto);
    	return "redirect:/usuarios";
    }
    
    @PreAuthorize("hasRole('ADMIN_CORP')")
    @GetMapping(value = "/usuarios/editar/{idUsuario}")
    public String edicionInit(final @PathVariable Long idUsuario, final Model model) {
    	model.addAttribute("usuarioDto", userService.findById(idUsuario));
    	model.addAttribute("rolesDto", rolService.findAll());
        return "usuarios/usuario-editar";
    }
    
    @PreAuthorize("hasRole('ADMIN_CORP')")
    @PostMapping(value = "/usuarios/editar")
    public String editar(final Locale locale, final Model model, @Valid final UsuarioDto usuarioDto, final BindingResult bindingResult) {
    	System.out.println("USUARIO: " + usuarioDto.toString());
    	System.out.println("ROLES: " + usuarioDto.getRolesSeleccionados().size());
    	userService.crearCuenta(usuarioDto);
    	return "redirect:/usuarios";
    }
    
    @PreAuthorize("hasRole('ADMIN_CORP')")
    @GetMapping(value = "/usuarios/eliminar/{idUsuario}")
    public String eliminar(final @PathVariable Long idUsuario, final Model model) {
    	userService.deleteById(idUsuario);
        return "redirect:/usuarios";
    }
    


//    @PreAuthorize("hasRole('ADMIN_CORP')")
//    @GetMapping(value = "/catalogos/zona-crear")
//    public String crearZona(final ZonaDto zonaDto, final HttpSession session) {
//        session.setAttribute("usuariosAdminZona", rolService.findUsuariosByNombreRol("ROLE_ADMIN_ZONA"));
//        return "catalogos/zona-crear";
//    }
//
//    @PreAuthorize("hasRole('ADMIN_CORP')")
//    @PostMapping(value = "/catalogos/zona-crear")
//    public String guardarZona(final Locale locale, final Model model, @Valid final ZonaDto zonaDto, final BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "/catalogos/zona-crear";
//        }
//        if (zonaService.exist(zonaDto.getCodigo())) {
//            FieldError error;
//            error = new FieldError("zonaDto", "codigo", zonaDto.getCodigo(), false, null, null, messages.getMessage("mensage.zona.codigoexiste", null, locale));
//            bindingResult.addError(error);
//            return "/catalogos/zona-crear";
//        } else {
//            zonaService.save(zonaDto);
//        }
//
//        return "redirect:/catalogos/zonas";
//    }
//
//    @PreAuthorize("hasRole('ADMIN_CORP')")
//    @GetMapping(value = "/catalogos/zona-editar/{codigo}")
//    public String buscarZonaPorId(final @PathVariable String codigo, final Model model, final HttpSession session) {
//        session.setAttribute("usuariosAdminZona", rolService.findUsuariosByNombreRol("ROLE_ADMIN_ZONA"));
//        model.addAttribute("zonaDto", zonaService.findById(codigo));
//        return "catalogos/zona-editar";
//    }
//
//    @PreAuthorize("hasRole('ADMIN_CORP')")
//    @PostMapping(value = "/catalogos/zona-editar")
//    public String editarZona(final Locale locale, final Model model, @Valid final ZonaDto zonaDto, final BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "/catalogos/zona-editar";
//        }
//        zonaService.save(zonaDto);
//        return "redirect:/catalogos/zonas";
//    }
//
//    @PreAuthorize("hasRole('ADMIN_CORP')")
//    @GetMapping(value = "/catalogos/zona-eliminar/{codigo}")
//    public String eliminarZona(final @PathVariable String codigo) {
//        zonaService.deleteById(codigo);
//        return "redirect:/catalogos/zonas";
//    }

}

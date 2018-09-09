package mx.com.admoninmuebles.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.com.admoninmuebles.dto.MensajeContactoDto;
import mx.com.admoninmuebles.service.MensajeContactoService;

@Controller
public class MensajeContactoController {
	
    @Autowired
    private MessageSource messages;

    @Autowired
    private MensajeContactoService mensajeContactoService;

    
    @RequestMapping("/contacto")
    public String contacto(final MensajeContactoDto mensajeContactoDto) {
        return "/contacto/contacto";
    }

    @PostMapping(value = "/contacto")
    public String contactoMensajeCrear(final Locale locale, final Model model, @Valid final MensajeContactoDto mensajeContactoDto, final BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "/contacto";
        }
        redirectAttributes.addFlashAttribute("message", messages.getMessage("contacto.guarda.ok", null, locale));
        mensajeContactoService.save(mensajeContactoDto);
        return "redirect:/contacto";
    }
    
    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @RequestMapping("/contacto/mensajes")
    public String contactoMensajes(final Model model) {
    	model.addAttribute("mensajesContactoDto", mensajeContactoService.findAll());
        return "/contacto/contacto-mensajes";
    }
    
    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @RequestMapping("/contacto/mensaje-detalle/{id}")
    public String contactoMensajesDetalle(final @PathVariable long id, final Model model) {
    	model.addAttribute("mensajeContactoDto", mensajeContactoService.findById(id));
        return "/contacto/contacto-mensaje-detalle";
    }
    
    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @RequestMapping("/contacto/mensaje-atender/{id}")
    public String contactoMensajesAtender(final @PathVariable long id, final Model model) {
    	model.addAttribute("mensajeContactoDto", mensajeContactoService.findById(id));
        return "/contacto/contacto-mensaje-atender";
    }
    
    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @PostMapping(value = "/contacto/mensaje-atender")
    public String contactoMensajesAtender(final Locale locale, final Model model, @Valid final MensajeContactoDto mensajeContactoDto, final BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "/contacto/contacto-mensaje-atender";
        }
//        redirectAttributes.addFlashAttribute("message", messages.getMessage("contacto.atendido.ok", null, locale));
        mensajeContactoService.save(mensajeContactoDto);
        return "redirect:/contacto/mensajes";
    }
    
    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @RequestMapping("/contacto/mensaje-eliminar/{id}")
    public String contactoMensajesEliminar(final @PathVariable long id) {
    	mensajeContactoService.deleteById(id);
        return "redirect:/contacto/mensajes";
    }

}

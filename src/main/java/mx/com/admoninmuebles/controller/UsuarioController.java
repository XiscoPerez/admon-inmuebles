package mx.com.admoninmuebles.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import mx.com.admoninmuebles.dto.UsuarioDto;
import mx.com.admoninmuebles.service.UsuarioService;

@Controller
public class UsuarioController {

    @Autowired
    private MessageSource messages;

    @Autowired
    private UsuarioService userService;

    @GetMapping(value = "/crearUsuario")
    public String showForm(final UsuarioDto userDto) {
        return "crearUsuario";
    }

    @PostMapping(value = "/crearUsuario")
    public String crearUsuario(final Locale locale, final Model model, @Valid final UsuarioDto usuarioDto, final BindingResult bindingResult) {
        FieldError error;

        if (!usuarioDto.getPassword().equals(usuarioDto.getConfirmPassword())) {
            error = new FieldError("usuarioDto", "confirmPassword", messages.getMessage("message.password.notMatch", null, locale));
            bindingResult.addError(error);
            error = new FieldError("usuarioDto", "password", messages.getMessage("message.password.notMatch", null, locale));
            bindingResult.addError(error);
        } else {
            userService.save(usuarioDto);
        }

        if (bindingResult.hasErrors()) {
            return "crearUsuario";
        }

        model.addAttribute("message", messages.getMessage("message.success.crearUsuario", null, locale));
        return "redirect:/login";
    }

}

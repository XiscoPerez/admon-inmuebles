package mx.com.admoninmuebles.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import mx.com.admoninmuebles.dto.ComentarioDto;
import mx.com.admoninmuebles.service.ComentarioService;

@Controller
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping(value = "/crearComentario")
    public String showForm(final ComentarioDto comentarioDto) {
        return "crearComentario";
    }

    @PostMapping(value = "/crearComentario")
    public String crearComentario(final Locale locale, final Model model, @Valid final ComentarioDto comentarioDto, final BindingResult bindingResult) {
        comentarioService.save(comentarioDto);
        return "redirect:/crearComentario";
    }

}

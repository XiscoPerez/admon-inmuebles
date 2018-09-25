package mx.com.admoninmuebles.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import mx.com.admoninmuebles.dto.PreguntaFrecuenteDto;
import mx.com.admoninmuebles.service.PreguntaFrecuenteService;

@Controller
public class PreguntaFrecuenteController {

    @Autowired
    private PreguntaFrecuenteService preguntaFrecuenteService;

    @GetMapping(value = "/crearPreguntaFrecuente")
    public String showForm(final PreguntaFrecuenteDto preguntaFrecuenteDto) {
        return "crearPreguntaFrecuente";
    }

    @PostMapping(value = "/crearPreguntaFrecuente")
    public String crearPreguntaFrecuente(final Locale locale, final Model model, @Valid final PreguntaFrecuenteDto preguntaFrecuenteDto, final BindingResult bindingResult) {
        preguntaFrecuenteService.save(preguntaFrecuenteDto);
        return "redirect:/crearPreguntaFrecuente";
    }
    
    @GetMapping(value = "/catalogos/preguntas-frecuentes")
    public String init(final PreguntaFrecuenteDto preguntaFrecuenteDto, Model model) {
    	
    	model.addAttribute("preguntasFrecuentes", preguntaFrecuenteService.findAll());
    	return "catalogos/preguntas-frecuentes";
    }
    
    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @PostMapping(value = "/catalogos/preguntas-frecuentes")
    public String guardarPreguntasFrecuentes(final Locale locale, final Model model, @Valid final PreguntaFrecuenteDto preguntaFrecuenteDto, final BindingResult bindingResult) {
    	preguntaFrecuenteService.save(preguntaFrecuenteDto);
        return "redirect:/catalogos/preguntas-frecuentes";
    }
    
    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/catalogos/preguntas-frecuentes-editar/{idPreguntaFrecuente}")
    public String buscarPreguntasFrecuentesPorId(final @PathVariable long idPreguntaFrecuente, Model model) {
    	model.addAttribute("preguntaFrecuenteDto", preguntaFrecuenteService.findById(idPreguntaFrecuente));
        return "catalogos/preguntas-frecuentes-edicion";
    }
    
    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @PostMapping(value = "/catalogos/preguntas-frecuentes-editar")
    public String editarPreguntasFrecuentes(final Locale locale, final Model model, @Valid final PreguntaFrecuenteDto preguntaFrecuenteDto, final BindingResult bindingResult) {
    	preguntaFrecuenteService.save(preguntaFrecuenteDto);
        return "redirect:/catalogos/preguntas-frecuentes";
    }
    
    @PreAuthorize("hasAnyRole('ADMIN_CORP', 'ADMIN_ZONA', 'ADMIN_BI')")
    @GetMapping(value = "/catalogos/preguntas-frecuentes-eliminar/{idPreguntaFrecuente}")
    public String eliminarPreguntasFrecuentes(final @PathVariable long idPreguntaFrecuente) {
    	preguntaFrecuenteService.deleteById(idPreguntaFrecuente);;
        return "redirect:/catalogos/preguntas-frecuentes";
    }

}

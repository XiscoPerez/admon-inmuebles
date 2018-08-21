package mx.com.admoninmuebles.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import mx.com.admoninmuebles.dto.ZonaDto;
import mx.com.admoninmuebles.service.ZonaService;

@Controller
public class ZonaController {

    @Autowired
    private ZonaService zonaService;

    @GetMapping(value = "/crearZona")
    public String showForm(final ZonaDto zonaDto) {
        return "crearZona";
    }

    @PostMapping(value = "/crearZona")
    public String crearZona(final Locale locale, final Model model, @Valid final ZonaDto zonaDto, final BindingResult bindingResult) {
        zonaService.save(zonaDto);
        return "redirect:/crearZona";
    }
    
    @GetMapping(value = "/catalogos/zona")
    public String init(final ZonaDto zonaDto, Model model) {
    	
    	model.addAttribute("zonas", zonaService.findAll());
    	return "catalogos/zona";
    }
    
    @PostMapping(value = "/catalogos/zona")
    public String guardarZona(final Locale locale, final Model model, @Valid final ZonaDto zonaDto, final BindingResult bindingResult) {
    	zonaService.save(zonaDto);
        return "redirect:/catalogos/zona";
    }
    
    @GetMapping(value = "/catalogos/zona-editar/{idZona}")
    public String buscarZonaPorId(final @PathVariable long idZona, Model model) {
    	model.addAttribute("zonaDto", zonaService.findById(idZona));
        return "catalogos/zona-edicion";
    }
    
    @PostMapping(value = "/catalogos/zona-editar")
    public String editarZona(final Locale locale, final Model model, @Valid final ZonaDto zonaDto, final BindingResult bindingResult) {
    	zonaService.save(zonaDto);
        return "redirect:/catalogos/zona";
    }
    
    @GetMapping(value = "/catalogos/zona-eliminar/{idZona}")
    public String eliminarZona(final @PathVariable long idZona) {
    	zonaService.deleteById(idZona);
        return "redirect:/catalogos/zona";
    }

}

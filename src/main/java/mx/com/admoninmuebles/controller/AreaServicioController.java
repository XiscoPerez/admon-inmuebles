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

import mx.com.admoninmuebles.dto.AreaServicioDto;
import mx.com.admoninmuebles.service.AreaServicioService;

@Controller
public class AreaServicioController {

    @Autowired
    private AreaServicioService areaServicioService;

    @GetMapping(value = "/crearAreaServicio")
    public String showForm(final AreaServicioDto areaServicioDto) {
        return "crearAreaServicio";
    }

    @PostMapping(value = "/crearAreaServicio")
    public String crearAreaServicio(final Locale locale, final Model model, @Valid final AreaServicioDto areaServicioDto, final BindingResult bindingResult) {
        areaServicioService.save(areaServicioDto);
        return "redirect:/crearAreaServicio";
    }

    @PostMapping(value = "/catalogos/areas-servicio")
    public String guardarAreaServicio(final Locale locale, final Model model, @Valid final AreaServicioDto areaServicioDto, final BindingResult bindingResult) {
        System.out.println("Guardando Area de Servicio " + areaServicioDto.getNombre());
        areaServicioService.save(areaServicioDto);
        return "redirect:/catalogos/areas-servicio";
    }

    @GetMapping(value = "/catalogos/areas-servicio")
    public String init(final AreaServicioDto areaServicioDto, final Model model) {

        model.addAttribute("areasServicio", areaServicioService.findAll());
        return "catalogos/areas-servicio";
    }

    @GetMapping(value = "/catalogos/areas-servicio-edicion/{idAreaServicio}")
    public String buscarAreaServicioPorId(final @PathVariable long idAreaServicio, final Model model) {

        model.addAttribute("areaServicioDto", areaServicioService.findAreaServicioById(idAreaServicio));
        return "catalogos/areas-servicio-edicion";
    }

    @PostMapping(value = "/catalogos/editar-areas-servicio")
    public String editarAreaServicio(final Locale locale, final Model model, @Valid final AreaServicioDto areaServicioDto, final BindingResult bindingResult) {
        System.out.println("Editando Area de Servicio " + areaServicioDto.getNombre());
        System.out.println("Editando Area de Servicio " + areaServicioDto.getId());
        areaServicioService.save(areaServicioDto);
        return "redirect:/catalogos/areas-servicio";
    }

    @GetMapping(value = "/catalogos/eliminar-areas-servicio/{idAreaServicio}")
    public String eliminarAreaServicio(final @PathVariable long idAreaServicio) {
        areaServicioService.deleteAreaServicio(idAreaServicio);
        ;
        return "redirect:/catalogos/areas-servicio";
    }

}

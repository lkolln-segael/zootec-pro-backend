package zootecpro.backend.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import zootecpro.backend.models.Rol;
import zootecpro.backend.models.dto.EstabloForm;
import zootecpro.backend.models.dto.UsuarioSimplified;
import zootecpro.backend.models.establo.Establo;
import zootecpro.backend.services.EstabloService;
import zootecpro.backend.services.UsuarioService;

@Controller
@RestController
@RequiredArgsConstructor
@Slf4j
public class EstabloController {

  private final EstabloService establoService;
  private final UsuarioService usuarioService;

  @GetMapping("/admin/establos")
  public ModelAndView getEstablos() {
    ModelAndView model = new ModelAndView("/admin/establos");
    List<Establo> establos = establoService.getEstablos();
    List<UsuarioSimplified> usuarioSimplifieds = usuarioService.getAllUsers()
        .stream().filter(u -> u.getRol().getNombre().toUpperCase().equals("ADMIN"))
        .map(u -> UsuarioSimplified.builder().id(u.getId())
            .nombre(u.getNombre())
            .nombreUsuario(u.getNombreUsuario())
            .build())
        .toList();
    List<String> sistemasProduccion = List.of("LECHERA", "BOVINA");
    model.addObject("activaPage", "establos");
    model.addObject("sistemasProduccion", sistemasProduccion);
    model.addObject("establos", establos);
    model.addObject("usuarios", usuarioSimplifieds);
    return model;
  }

  @PostMapping("/admin/establos")
  public ModelAndView postEstablos(@ModelAttribute EstabloForm establoForm) {
    ModelAndView model = new ModelAndView("redirect:/admin/establos");
    this.establoService.insertEstablo(establoForm);
    return model;
  }

  @GetMapping("/admin/establos/view/{id}")
  public ModelAndView viewEstablo(@PathVariable String id) {
    ModelAndView model = new ModelAndView("/admin/establos/view");
    model.addObject("activaPage", "establos");
    Establo establo = establoService.getEstabloById(id).get();
    model.addObject("establo", establo);
    return model;
  }

  @GetMapping("/admin/establos/edit/{id}")
  public ModelAndView editEstablo(@PathVariable String id, @ModelAttribute EstabloForm establoForm) {
    ModelAndView model = new ModelAndView("/admin/establos/edit");
    model.addObject("activaPage", "establos");
    Establo establo = establoService.getEstabloById(id).get();
    model.addObject("establo", establo);
    return model;
  }

  @PutMapping("/admin/establos/edit/{id}")
  public ModelAndView putEstablo(@PathVariable String id, @ModelAttribute EstabloForm establoForm) {
    ModelAndView model = new ModelAndView("redirect:/admin/establos/view");
    Establo establo = establoService.getEstabloById(id).get();
    model.addObject("activaPage", "establos");
    model.addObject("establo", establo);
    this.establoService.updateEstablo(id, establoForm);
    return model;
  }
}

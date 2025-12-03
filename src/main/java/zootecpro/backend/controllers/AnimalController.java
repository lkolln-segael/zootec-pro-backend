package zootecpro.backend.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import zootecpro.backend.models.dto.AnimalForm;
import zootecpro.backend.models.dto.TipoAnimalForm;
import zootecpro.backend.services.AnimalService;

@Controller
@RequiredArgsConstructor
@RestController
@Slf4j
public class AnimalController {

  private final AnimalService service;

  @GetMapping("/admin/animales")
  public ModelAndView getAnimales() {
    ModelAndView model = new ModelAndView("admin/animales");
    model.addObject("activePage", "animales");
    return model;
  }

  @GetMapping("/admin/animales/tipo")
  public ModelAndView getTipoAnimales() {
    ModelAndView model = new ModelAndView("admin/animales");
    var tiposAnimal = service.getAllTiposAnimal();
    log.debug(tiposAnimal.toString());
    model.addObject("activePage", "animales");
    model.addObject("tiposAnimal", tiposAnimal);
    return model;
  }

  @PostMapping("/admin/animales/tipo")
  public ModelAndView insertTipoAnimal(@ModelAttribute TipoAnimalForm tipo) {
    ModelAndView model = new ModelAndView("admin/animales");
    this.service.insertTipoAnimal(tipo);
    var tiposAnimal = service.getAllTiposAnimal();
    log.info(tiposAnimal.toString());
    model.addObject("tiposAnimal", tiposAnimal);
    model.addObject("activePage", "animales");
    return model;
  }

  @PutMapping("/admin/animales/tipo/{id}")
  public ModelAndView updateTipoAnimal(@PathVariable String id, @ModelAttribute TipoAnimalForm tipo) {
    ModelAndView model = new ModelAndView("admin/animales");
    this.service.updateTipoAnimal(id, tipo);
    var tiposAnimal = service.getAllTiposAnimal();
    log.info(tiposAnimal.toString());
    model.addObject("tiposAnimal", tiposAnimal);
    model.addObject("activePage", "animales");
    return model;
  }

  @DeleteMapping("/admin/animales/tipo/{id}")
  public ModelAndView deleteTipoAnimal(@PathVariable String id) {
    ModelAndView model = new ModelAndView("admin/animales");
    this.service.deleteTipoAnimal(id);
    var tiposAnimal = service.getAllTiposAnimal();
    log.info("DELETE: " + tiposAnimal.toString());
    model.addObject("tiposAnimal", tiposAnimal);
    model.addObject("activePage", "animales");
    return model;
  }

  @GetMapping("/admin/animales/add")
  public ModelAndView addAnimal(@RequestParam String establoId) {
    ModelAndView model = new ModelAndView("admin/animales/add");
    var tiposAnimal = service.getAllTiposAnimal();
    var propositos = List.of("CRIA", "LECHE", "CARNE");
    model.addObject("propositos", propositos);
    model.addObject("activePage", "animales");
    model.addObject("tiposAnimal", tiposAnimal);
    model.addObject("establoId", establoId);
    return model;
  }

  @GetMapping("/admin/animales/edit/{id}")
  public ModelAndView editAnimal(@RequestParam String establoId, @PathVariable String id) {
    ModelAndView model = new ModelAndView("admin/animales/edit");
    var tiposAnimal = service.getAllTiposAnimal();
    var propositos = List.of("CRIA", "LECHE", "CARNE");
    var animalOpt = service.getAnimalById(id);
    if (animalOpt.isEmpty()) {
      return new ModelAndView("redirect:/admin/establos/" + establoId);
    }
    model.addObject("propositos", propositos);
    model.addObject("activePage", "animales");
    model.addObject("tiposAnimal", tiposAnimal);
    model.addObject("animal", animalOpt.get());
    model.addObject("establoId", establoId);
    return model;
  }

  @PostMapping("/admin/animales/add")
  public ModelAndView saveAnimal(@RequestParam String establoId, @ModelAttribute AnimalForm tipo) {
    ModelAndView model = new ModelAndView("redirect:/admin/establos/view/" + establoId);
    this.service.insertAnimal(establoId, tipo);
    return model;
  }

  @GetMapping("/admin/animales/view/{id}")
  public ModelAndView viewAnimal(@PathVariable String id) {
    ModelAndView model = new ModelAndView("admin/animales/view");
    var animalOpt = service.getAnimalById(id);
    if (animalOpt.isEmpty()) {
      return new ModelAndView("redirect:/admin/animales");
    }
    model.addObject("activePage", "animales");
    model.addObject("animal", animalOpt.get());
    return model;
  }

  @PutMapping("/admin/animales/edit/{id}")
  public ModelAndView editAnimal(@RequestParam String establoId, @PathVariable String id,
      @ModelAttribute AnimalForm tipo) {
    ModelAndView model = new ModelAndView("redirect:/admin/establos/view/" + establoId);
    this.service.updateAnimal(establoId, id, tipo);
    return model;
  }
}

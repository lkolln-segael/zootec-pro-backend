package zootecpro.backend.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import zootecpro.backend.models.api.ApiResponse;
import zootecpro.backend.models.dto.AnimalExtended;
import zootecpro.backend.models.dto.AnimalForm;
import zootecpro.backend.models.dto.TipoAnimalForm;
import zootecpro.backend.models.establo.Animal;
import zootecpro.backend.models.establo.TipoAnimal;
import zootecpro.backend.services.AnimalService;

@Controller
@RequiredArgsConstructor
@RestController
@Slf4j
@Tag(name = "Animal", description = "API de gestión de animales")
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

  @GetMapping("/api/animales/tipo/list")
  public ResponseEntity<ApiResponse<List<TipoAnimal>>> getTipoAnimalesApi() {
    List<TipoAnimal> tiposAnimal = service.getAllTiposAnimal();
    ApiResponse<List<TipoAnimal>> response = ApiResponse.<List<TipoAnimal>>builder()
        .data(tiposAnimal)
        .build();
    return ResponseEntity.ok(response);
  }

  @PostMapping("/api/animales/add/extended")
  public ResponseEntity<ApiResponse<String>> insertAnimalExtendedApi(@RequestParam String establoId,
      @RequestBody AnimalExtended animalExtended) {
    this.service.insertAnimalExtended(establoId, animalExtended);
    ApiResponse<String> response = ApiResponse.<String>builder()
        .message("Animal creado con éxito")
        .build();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/api/animales/all")
  public ResponseEntity<ApiResponse<List<Animal>>> getAllAnimalesApi(
      @RequestParam String establoId) {
    List<Animal> animales = service.getAllAnimals(establoId);
    return ResponseEntity.ok(
        ApiResponse.<List<Animal>>builder()
            .data(animales)
            .build());
  }

  @PostMapping("/api/animales/add")
  public ResponseEntity<ApiResponse<String>> insertAnimalApi(@RequestParam String establoId,
      @RequestBody AnimalForm animalForm) {
    this.service.insertAnimal(establoId, animalForm);
    ApiResponse<String> response = ApiResponse.<String>builder()
        .message("Animal creado con éxito")
        .build();
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/admin/animales/delete/{id}")
  public ModelAndView deleteAnimalApi(@PathVariable String id) {
    this.service.deleteAnimal(id);
    return new ModelAndView("redirect:/admin/establos");
  }
}

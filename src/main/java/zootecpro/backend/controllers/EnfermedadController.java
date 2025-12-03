package zootecpro.backend.controllers;

import zootecpro.backend.models.dto.EnfermedadForm;
import zootecpro.backend.models.dto.SintomaForm;
import zootecpro.backend.models.dto.TipoEnfermedadForm;
import zootecpro.backend.models.dto.TratamientoForm;
import zootecpro.backend.models.enfermedad.Enfermedad;
import zootecpro.backend.models.enfermedad.TipoEnfermedad;
import zootecpro.backend.models.establo.TipoAnimal;
import zootecpro.backend.services.AnimalService;
import zootecpro.backend.services.EnfermedadService;

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

@Slf4j
@Controller
@RestController
@RequiredArgsConstructor
@Tag(name = "Enfermedad", description = "API de gestión de enfermedades")
public class EnfermedadController {

  private final EnfermedadService service;
  private final AnimalService animalService;

  @GetMapping("/admin/enfermedades/tipo")
  public ModelAndView enfermedadForm() {
    ModelAndView model = new ModelAndView("admin/enfermedades");
    List<TipoEnfermedad> enfermedades = service.getAllTiposEnfermedades();
    List<TipoAnimal> animales = animalService.getAllTiposAnimal();
    model.addObject("tipoEnfermedades", enfermedades);
    model.addObject("tipoAnimales", animales);
    model.addObject("activePage", "enfermedades");
    return model;
  }

  @PostMapping("/admin/enfermedades/tipo")
  public ModelAndView postEnfermedad(@ModelAttribute TipoEnfermedadForm enfermedad) {
    ModelAndView model = new ModelAndView("admin/enfermedades");
    this.service.insertTipoEnfermedad(enfermedad);
    List<TipoEnfermedad> enfermedades = service.getAllTiposEnfermedades();
    List<TipoAnimal> animales = animalService.getAllTiposAnimal();
    log.info(enfermedades.toString());
    model.addObject("tipoAnimales", animales);
    model.addObject("tipoEnfermedades", enfermedades);
    model.addObject("activePage", "enfermedades");
    return model;
  }

  @PutMapping("/admin/enfermedades/tipo/{id}")
  public ModelAndView putTipoEnfermedad(@ModelAttribute TipoEnfermedadForm enfermedad, @PathVariable String id) {
    ModelAndView model = new ModelAndView("admin/enfermedades");
    this.service.updateTipoEnfermedad(id, enfermedad);
    List<TipoEnfermedad> enfermedades = service.getAllTiposEnfermedades();
    List<TipoAnimal> animales = animalService.getAllTiposAnimal();
    log.info(enfermedades.toString());
    model.addObject("tipoAnimales", animales);
    model.addObject("tipoEnfermedades", enfermedades);
    model.addObject("activePage", "enfermedades");
    return model;
  }

  @PostMapping("/admin/enfermedades/tipo/tratamientos/{id}")
  public ResponseEntity<String> insertTratamientos(@PathVariable String id,
      @RequestBody List<TratamientoForm> tratamientos) {
    this.service.insertTratamientos(id, tratamientos);
    List<TipoEnfermedad> enfermedades = service.getAllTiposEnfermedades();
    List<TipoAnimal> animales = animalService.getAllTiposAnimal();
    return ResponseEntity.ok("Tratamientos agregados correctamente");
  }

  @PostMapping("/admin/enfermedades/tipo/sintomas/{id}")
  public ResponseEntity<String> insertSintomas(@PathVariable String id, @RequestBody List<SintomaForm> sintomas) {
    boolean result = this.service.insertSintomas(id, sintomas);
    List<TipoEnfermedad> enfermedades = service.getAllTiposEnfermedades();
    List<TipoAnimal> animales = animalService.getAllTiposAnimal();
    return ResponseEntity.ok("Sintomas agregados correctamente");
  }

  @DeleteMapping("/admin/enfermedades/tipo/{id}")
  public ModelAndView deleteTipoEnfermedad(@PathVariable String id) {
    ModelAndView model = new ModelAndView("admin/enfermedades");
    this.service.deleteTipoEnfermedad(id);
    List<TipoEnfermedad> enfermedades = service.getAllTiposEnfermedades();
    List<TipoAnimal> animales = animalService.getAllTiposAnimal();
    log.info(enfermedades.toString());
    model.addObject("tipoAnimales", animales);
    model.addObject("tipoEnfermedades", enfermedades);
    model.addObject("activePage", "enfermedades");
    return model;
  }

  @GetMapping("/admin/enfermedades/registrar")
  public ModelAndView registrarEnfermedadForm(@RequestParam String animalId,
      @ModelAttribute EnfermedadForm enfermedad) {
    ModelAndView model = new ModelAndView("admin/enfermedades/registrar");
    List<TipoEnfermedad> enfermedades = service.getAllTiposEnfermedades();
    service.insertEnfermedad(animalId, enfermedad);
    model.addObject("tipoEnfermedades", enfermedades);
    model.addObject("activePage", "enfermedades");
    return model;
  }
}

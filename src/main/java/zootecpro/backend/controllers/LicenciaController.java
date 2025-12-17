package zootecpro.backend.controllers;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import zootecpro.backend.models.dto.usuario.LicenciaForm;
import zootecpro.backend.services.LicenciaService;

@Controller
@RestController
@RequiredArgsConstructor
@Slf4j
public class LicenciaController {

  private final LicenciaService licenciaService;

  @GetMapping("/admin/licencias")
  public ModelAndView licenciasPage() {
    ModelAndView model = new ModelAndView("admin/licencias");
    log.info("Accediendo a la página de licencias");
    model.addObject("licencias", licenciaService.getAllLicencias());
    model.addObject("activePage", "licencias");
    return model;
  }

  @GetMapping("/admin/licencias/edit/{id}")
  public ModelAndView licenciasEditPage(@PathVariable String id) {
    ModelAndView model = new ModelAndView("admin/licencias/edit");
    log.info("Accediendo a la página de edición de licencias");
    model.addObject("licencia", licenciaService.getLicenciaById(UUID.fromString(id)));
    model.addObject("activePage", "licencias");
    return model;
  }

  @PostMapping("/admin/licencias")
  public ModelAndView licenciasPost(@ModelAttribute LicenciaForm licenciaForm) {
    ModelAndView model = new ModelAndView("redirect:/admin/licencias");
    licenciaService.insertLicencia(licenciaForm);
    log.info("Accediendo a la página de licencias");
    return model;
  }

  @PutMapping("/admin/licencias/edit/{id}")
  public ModelAndView licenciasEdit(@PathVariable String id, @ModelAttribute LicenciaForm licenciaForm) {
    ModelAndView model = new ModelAndView("redirect:/admin/licencias");
    licenciaService.updateLicencia(licenciaForm);
    log.info("Accediendo a la página de licencias");
    return model;
  }

  @DeleteMapping("/admin/licencias/delete/{id}")
  public ModelAndView licenciasDelete(@PathVariable String id) {
    ModelAndView model = new ModelAndView("redirect:/admin/licencias");
    licenciaService.deleteLicencia(UUID.fromString(id));
    log.info("Accediendo a la página de licencias");
    return model;
  }
}

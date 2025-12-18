package zootecpro.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ArrayNode;

import lombok.RequiredArgsConstructor;
import zootecpro.backend.models.api.ApiResponse;
import zootecpro.backend.models.dto.reproduccion.AbortoForm;
import zootecpro.backend.models.dto.reproduccion.ConfirmacionPrenezForm;
import zootecpro.backend.models.dto.reproduccion.PartoForm;
import zootecpro.backend.models.dto.reproduccion.PrenezForm;
import zootecpro.backend.models.dto.reproduccion.SecaForm;
import zootecpro.backend.services.ReproduccionService;

@Controller
@RestController
@RequiredArgsConstructor
public class ReproduccionController {
  private final ReproduccionService reproduccionService;

  @GetMapping("/api/reproducciones")
  public ResponseEntity<ApiResponse<ArrayNode>> getAllReproducciones(@RequestParam String animalId) {
    ArrayNode reproducciones = reproduccionService.getRegistroReproduccionAnimal(animalId);
    return ResponseEntity.ok(ApiResponse.<ArrayNode>builder()
        .data(reproducciones)
        .message("Registro de reproducciones obtenido exitosamente")
        .build());
  }

  @PostMapping("/api/reproducciones/seca")
  public ResponseEntity<ApiResponse<Void>> insertarPeriodoSeca(@RequestParam String animalId,
      @RequestBody SecaForm secaForm) {
    reproduccionService.insertarRegistroReproduccionSeca(secaForm, animalId);
    return ResponseEntity.ok(ApiResponse.<Void>builder()
        .message("Periodo de seca insertado exitosamente")
        .build());
  }

  @PostMapping("/api/reproducciones/parto")
  public ResponseEntity<ApiResponse<Void>> insertarRegistroParto(@RequestParam String animalId,
      @RequestBody PartoForm partoForm) {
    reproduccionService.insertarRegistroReproduccionParto(partoForm, animalId);
    return ResponseEntity.ok(ApiResponse.<Void>builder()
        .message("Registro de parto insertado exitosamente")
        .build());
  }

  @PostMapping("/api/reproducciones/confirmacion-prenez")
  public ResponseEntity<ApiResponse<Void>> insertarConfirmacionPrenez(@RequestParam String animalId,
      @RequestBody ConfirmacionPrenezForm confirmacionPrenezForm) {
    reproduccionService.insertarRegistroReproduccionConfirmacionPrenez(confirmacionPrenezForm, animalId);
    return ResponseEntity.ok(ApiResponse.<Void>builder()
        .message("Confirmación de preñez insertada exitosamente")
        .build());
  }

  @PostMapping("/api/reproducciones/prenez")
  public ResponseEntity<ApiResponse<Void>> insertarRegistroPrenez(@RequestParam String animalId,
      @RequestBody PrenezForm prenezForm) {
    reproduccionService.insertarRegistroReproduccionPrenez(prenezForm, animalId);
    return ResponseEntity.ok(ApiResponse.<Void>builder()
        .message("Registro de preñez insertado exitosamente")
        .build());
  }

  @PostMapping("/api/reproducciones/aborto")
  public ResponseEntity<ApiResponse<Void>> insertarRegistroAborto(@RequestParam String animalId,
      @RequestBody AbortoForm abortoForm) {
    reproduccionService.insertarRegistroReproduccionAborto(abortoForm, animalId);
    return ResponseEntity.ok(ApiResponse.<Void>builder()
        .message("Registro de aborto insertado exitosamente")
        .build());
  }
}

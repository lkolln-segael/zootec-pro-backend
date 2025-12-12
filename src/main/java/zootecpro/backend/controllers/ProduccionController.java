package zootecpro.backend.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.lang.Collections;
import lombok.RequiredArgsConstructor;
import zootecpro.backend.models.api.ApiResponse;
import zootecpro.backend.models.dto.ProduccionForm;
import zootecpro.backend.models.registros.RegistroProduccion;
import zootecpro.backend.services.AnimalService;

@Controller
@RestController
@RequiredArgsConstructor
public class ProduccionController {
  private final AnimalService animalService;

  @GetMapping("/api/produccion/list")
  public ResponseEntity<ApiResponse<List<RegistroProduccion>>> getAllProduccion() {
    return ResponseEntity.ok(ApiResponse.<List<RegistroProduccion>>builder().data(
        this.animalService.getAllProducciones()).build());
  }

  @PostMapping("/api/produccion/add")
  public ResponseEntity<ApiResponse<String>> insertProduccion(@RequestBody ProduccionForm produccionForm) {
    this.animalService.insertProduccion(produccionForm);
    return ResponseEntity.ok(ApiResponse.<String>builder().data("Produccion insertada con exito").build());
  }

  @GetMapping("/api/produccion/lechera")
  public ResponseEntity<ApiResponse<List<RegistroProduccion>>> getProduccionByAño(@RequestParam String establoId,
      @RequestParam Integer año) {
    var produccion = this.animalService.getAllProducciones()
        .stream()
        .filter(p -> p.getAnimal().getEstablo().getId().equals(UUID.fromString(establoId))
            && p.getFechaRegistro().isAfter(LocalDateTime.of(año, 1, 1, 0, 0, 0)))
        .toList();
    return ResponseEntity.ok(ApiResponse.<List<RegistroProduccion>>builder()
        .data(produccion)
        .build());
  }
}

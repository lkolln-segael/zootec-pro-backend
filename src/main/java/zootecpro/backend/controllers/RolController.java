package zootecpro.backend.controllers;

import java.time.Duration;
import java.util.List;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import zootecpro.backend.models.Rol;
import zootecpro.backend.services.RolService;

@RestController
@Controller
@RequiredArgsConstructor
public class RolController {
  private final RolService rolService;

  @GetMapping("/api/rol/list")
  public ResponseEntity<List<Rol>> getAllRoles() {
    return ResponseEntity.status(200).eTag("\"" + System.currentTimeMillis() + "\"")
        .cacheControl(CacheControl.maxAge(Duration.ofDays(3)).cachePublic())
        .body(rolService.getRoles());
  }
}

package zootecpro.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import zootecpro.backend.models.dto.InsertUsuario;
import zootecpro.backend.models.dto.LoginUsuario;
import zootecpro.backend.services.UsuarioService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "Usuario", description = "API de gestión de usuarios")
public class UsuarioController {

  private final UsuarioService usuarioService;

  @PostMapping("/register")
  @Operation(summary = "Registrar nuevo usuario")
  public ResponseEntity<String> register(@RequestBody InsertUsuario usuario) { // ✅ Import correcto
    try {
      usuarioService.insertUsuario(usuario);
      return ResponseEntity.ok("Usuario registrado con éxito");
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Error al registrar usuario: " + e.getMessage());
    }
  }

  @PostMapping("/login")
  @Operation(summary = "Realizar inicio de sesion")
  public ResponseEntity<String> login(@RequestBody LoginUsuario usuario) {
    try {
      String token = usuarioService.verifyUsuario(usuario.nombreUsuario, usuario.contraseña);

      return ResponseEntity.ok(token);
    } catch (UsernameNotFoundException user) {
      return ResponseEntity.status(401).body(user.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Error al logear el usuario: " + e.getMessage());
    }
  }
}

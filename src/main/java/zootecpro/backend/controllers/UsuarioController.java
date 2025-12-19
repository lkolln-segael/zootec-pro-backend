package zootecpro.backend.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import zootecpro.backend.models.Usuario;
import zootecpro.backend.models.api.ApiResponse;
import zootecpro.backend.models.dto.usuario.InsertTrabajador;
import zootecpro.backend.models.dto.usuario.InsertUsuario;
import zootecpro.backend.models.dto.usuario.LoginUsuario;
import zootecpro.backend.models.dto.usuario.UsuarioSimplified;
import zootecpro.backend.services.UsuarioService;
import zootecpro.backend.services.RolService;

@RequiredArgsConstructor
@RestController
@Controller
@Slf4j
@Tag(name = "Usuario", description = "API de gestión de usuarios")
public class UsuarioController {

  private final UsuarioService usuarioService;
  private final RolService rolService;

  @PostMapping("/api/register")
  @Operation(summary = "Registrar nuevo usuario")
  public ResponseEntity<String> register(@RequestBody InsertUsuario usuario) {
    try {
      usuarioService.insertUsuario(usuario);
      return ResponseEntity.status(201).body("Usuario registrado con éxito");
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Error al registrar usuario: " + e.getMessage());
    }
  }

  @PostMapping("/api/login")
  @Operation(summary = "Realizar inicio de sesion")
  public ResponseEntity<Map<String, String>> login(@RequestBody LoginUsuario usuario) {
    try {
      IO.println(usuario.nombreUsuario);
      String token = usuarioService.verifyUsuario(usuario.nombreUsuario, usuario.contraseña);
      return ResponseEntity.ok(Map.of("token", token));
    } catch (UsernameNotFoundException user) {
      return ResponseEntity.status(401).body(Map.of("error", user.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.status(500).body(Map.of("error", "Error al logear el usuario: " + e.getMessage()));
    }
  }

  @PostMapping("/api/users/add")
  public ResponseEntity<ApiResponse<String>> insertUsuarioToEstablo(@RequestBody InsertTrabajador trabajador,
      @RequestParam String establoId) {
    return ResponseEntity.ok(ApiResponse.<String>builder()
        .message(this.usuarioService.insertTrabajador(trabajador,
            UUID.fromString(establoId)))
        .build());
  }

  @PutMapping("/api/users/edit/{usuarioId}")
  public ResponseEntity<String> editUsuarioToEstablo(@RequestParam String establoId,
      @PathVariable("usuarioId") String usuarioId,
      @RequestBody InsertTrabajador trabajador) {
    try {
      log.info("Editing usuario {} for establo {}", usuarioId, establoId);
      String result = this.usuarioService.editTrabajador(trabajador, UUID.fromString(establoId),
          UUID.fromString(usuarioId));
      log.info("Edit result: {}", result);
      return ResponseEntity.ok(result);
    } catch (IllegalArgumentException e) {
      log.info("Invalid UUID format: {}", e.getMessage());
      return ResponseEntity.badRequest().body("Invalid ID format");
    } catch (Exception e) {
      log.error("Error editing trabajador: ", e); // Fixed the stack trace logging
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error en el servidor: " + e.getMessage());
    }
  }

  @GetMapping("/api/users")
  @Operation(summary = "Obtener usuarios")
  public ResponseEntity<List<UsuarioSimplified>> getUsuariosByEstablo(@RequestParam String establoId) {
    return ResponseEntity.ok(usuarioService.getTrabajadores(UUID.fromString(establoId)));
  }

  @GetMapping("/admin/login")
  public ModelAndView loginForm() {
    return new ModelAndView("admin/login");
  }

  @GetMapping("/admin/users/rol/view/{id}")
  @Operation(summary = "Obtener usuario por ID")
  public ModelAndView getUsuarioRolById(@PathVariable String id) {
    ModelAndView model = new ModelAndView("admin/rol-view");
    try {
      log.debug("Cargando User con ID: {}", id);
      Usuario usuario = usuarioService.getUsuarioById(UUID.fromString(id));
      model.addObject("rol", usuario.getRol());
      return model;
    } catch (Exception e) {
      log.error(e.getMessage());
      log.error(e.getStackTrace().toString());
      return model;
    }
  }

  @GetMapping("/admin/users")
  @Operation(summary = "Obtener todos los usuarios")
  public ModelAndView getAllUsuarios() {
    ModelAndView model = new ModelAndView("admin/users");
    try {
      log.debug("Cargando Users");
      List<UsuarioSimplified> result = usuarioService.getAllUsers().stream()
          .map(usuario -> {
            return UsuarioSimplified.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .licencia(Optional.ofNullable(usuario.getLicencia()))
                .rol(usuario.getRol())
                .nombreUsuario(usuario.getNombreUsuario())
                .build();
          }).toList();
      List<String> roles = rolService.getRolesName();
      model.addObject("roles", roles);
      model.addObject("success", true);
      model.addObject("activePage", "users");
      model.addObject("users", result);
      return model;
    } catch (Exception e) {
      log.error(e.getMessage());
      log.error(e.getStackTrace().toString());
      model.addObject("success", false);
      return model;
    }
  }

  @PostMapping("/admin/users")
  public ModelAndView postUsuarios(@ModelAttribute InsertUsuario nuevoUsuario) {
    ModelAndView model = new ModelAndView("admin/users");
    try {
      log.debug("Cargando Users");
      List<UsuarioSimplified> result = usuarioService.getAllUsers().stream()
          .map(usuario -> {
            return UsuarioSimplified.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .licencia(Optional.ofNullable(usuario.getLicencia()))
                .rol(usuario.getRol())
                .nombreUsuario(usuario.getNombreUsuario())
                .build();
          }).toList();
      List<String> roles = rolService.getRolesName();
      model.addObject("roles", roles);
      model.addObject("activePage", "users");
      model.addObject("users", result);
      log.debug("Creando nuevo usuario: {}", nuevoUsuario.getNombreUsuario());
      usuarioService.insertUsuario(nuevoUsuario);
      model.addObject("success", true);
      model.addObject("message", "Usuario creado con éxito");
      return model;
    } catch (Exception e) {
      log.error(e.getMessage());
      log.error(e.getStackTrace().toString());
      model.addObject("success", false);
      model.addObject("message", "Error al crear el usuario: " + e.getMessage());
      return model;
    }
  }

  @GetMapping("/admin/users/edit/{id}")
  public ModelAndView getEditUsuarios(@PathVariable String id) {
    ModelAndView model = new ModelAndView("admin/users/edit");
    try {
      log.debug("Cargando User con ID: {}", id);
      Usuario usuario = usuarioService.getUsuarioById(UUID.fromString(id));
      List<String> roles = rolService.getRolesName();
      model.addObject("roles", roles);
      model.addObject("user", usuario);
      return model;
    } catch (Exception e) {
      log.error(e.getMessage());
      log.error(e.getStackTrace().toString());
      return model;
    }
  }

  @PutMapping("/admin/users/edit/{id}")
  public ModelAndView putUsuarios(@PathVariable String id, @ModelAttribute InsertUsuario updatedUsuario) {
    ModelAndView model = new ModelAndView("redirect:/admin/users");
    try {
      log.info("Actualizando usuario con ID: {}", id);
      usuarioService.updateUsuario(UUID.fromString(id), updatedUsuario);
      return model;
    } catch (Exception e) {
      log.error(e.getMessage());
      log.error(e.getStackTrace().toString());
      model.addObject("success", false);
      model.addObject("message", "Error al actualizar el usuario: " + e.getMessage());
      return model;
    }
  }

  @DeleteMapping("/admin/users/delete/{id}")
  public ModelAndView deleteUsuario(@PathVariable String id) {
    ModelAndView model = new ModelAndView("redirect:/admin/users");
    try {
      log.debug("Eliminando usuario con ID: {}", id);
      usuarioService.deleteUsuario(UUID.fromString(id));
      return model;
    } catch (Exception e) {
      log.error(e.getMessage());
      log.error(e.getStackTrace().toString());
      model.addObject("success", false);
      model.addObject("message", "Error al eliminar el usuario: " + e.getMessage());
      return model;
    }
  }
}

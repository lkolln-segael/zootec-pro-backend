package zootecpro.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import zootecpro.backend.models.Rol;
import zootecpro.backend.models.Usuario;
import zootecpro.backend.models.dto.InsertUsuario;
import zootecpro.backend.repositories.*;

@Slf4j
@Service
public class UsuarioService implements UserDetailsService {

  private final UsuarioRepository repository;

  private final RolRepository rolRepository;

  private final JwtService jwtService;

  private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  public UsuarioService(UsuarioRepository repository, RolRepository rolRepository, JwtService jwtService) {
    this.repository = repository;
    this.rolRepository = rolRepository;
    this.jwtService = jwtService;
    createSuperAdmin();
  }

  private void createSuperAdmin() {
    if (this.repository.existsUsuarioByRol("SUPERADMIN")) {
      return;
    }
    log.info("Superadmin creando");
    Optional<Rol> rolOpt = this.rolRepository.findByNombre("SUPERADMIN");
    if (!rolOpt.isPresent()) {
      Rol rol = Rol.builder()
          .id(UUID.randomUUID())
          .nombre("admin")
          .permisos(List.of("READ_UsuariosAdmin",
              "WRITE_UsuariosAdmin", "UPDATE_UsuariosAdmin", "DELETE_UsuariosAdmin"))
          .build();
      rolRepository.save(rol);
      rolOpt = Optional.of(rol);
    }
    Rol rol = rolOpt.get();
    this.repository.save(Usuario.builder()
        .id(UUID.randomUUID())
        .nombre("admin")
        .contraseña(encoder.encode("admin123"))
        .nombreUsuario("admin")
        .rol(rol)
        .build());
    log.info("Superadmin creado");
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Usuario> usuarioOpt = repository.findByNombreUsuario(username);
    log.info("Buscando usuario");
    if (!usuarioOpt.isPresent()) {
      throw new UsernameNotFoundException("Usuario no encontrado");
    }
    log.info(usuarioOpt.get().toString());
    return usuarioOpt.get();
  }

  private String encryptPassword(String password) {
    return encoder.encode(password);
  }

  public String verifyUsuario(String username, String password) throws UsernameNotFoundException {
    UserDetails usuario = loadUserByUsername(username);
    if (!encoder.matches(password, usuario.getPassword())) {
      throw new UsernameNotFoundException("Contraseña incorrecta");
    }
    return this.jwtService.generateToken(username);
  }

  public void insertUsuario(InsertUsuario usuarioDto) {
    String nombreRol = "";
    if (usuarioDto.rol.isPresent()) {
      switch (usuarioDto.rol.get()) {
        case ADMIN:
          nombreRol = "admin";
          break;
        case OPERARIO:
          nombreRol = "operario";
          break;
        case SUPERADMIN:
          nombreRol = "superadmin";
          break;
        case VETERINARIO:
          nombreRol = "veterinario";
          break;
        default:
          break;
      }
    } else {
      nombreRol = "operario";
    }

    Optional<Rol> optionalRol = this.rolRepository.findByNombre(nombreRol.toUpperCase());
    if (!optionalRol.isPresent()) {
      Rol rol = Rol.builder().id(UUID.randomUUID()).nombre(nombreRol.toUpperCase()).build();
      rolRepository.save(rol);
      optionalRol = Optional.of(rol);
    }
    Rol rol = optionalRol.get();
    Usuario usuario = Usuario.builder()
        .id(UUID.randomUUID())
        .contraseña(encryptPassword(usuarioDto.contraseña))
        .nombreUsuario(usuarioDto.nombreUsuario)
        .nombre(usuarioDto.nombre)
        .rol(rol)
        .build();
    repository.save(usuario);
  }
}

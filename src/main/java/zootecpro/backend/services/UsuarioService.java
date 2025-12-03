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
      Rol rol = Rol.builder().id(UUID.randomUUID())
          .nombre("SUPERADMIN")
          .permisos(
              List.of("READ_UsuariosAdmin", "WRITE_UsuariosAdmin", "UPDATE_UsuariosAdmin", "DELETE_UsuariosAdmin",
                  "READ_Reproduccion", "WRITE_Reproduccion", "UPDATE_Reproduccion", "DELETE_Reproduccion",
                  "READ_Crecimiento", "WRITE_Crecimiento", "UPDATE_Crecimiento", "DELETE_Crecimiento",
                  "READ_Sanidad", "WRITE_Sanidad", "UPDATE_Sanidad", "DELETE_Sanidad",
                  "READ_Establo", "WRITE_Establo", "UPDATE_Establo", "DELETE_Establo",
                  "READ_Enfermedades", "WRITE_Enfermedades", "UPDATE_Enfermedades", "DELETE_Enfermedades"))
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

  public List<Usuario> getAllUsers() {
    return repository.findAll();
  }

  public Usuario getUsuarioById(UUID id) throws UsernameNotFoundException {
    Optional<Usuario> usuarioOpt = repository.findById(id);
    if (usuarioOpt.isPresent()) {
      return usuarioOpt.get();
    } else {
      throw new UsernameNotFoundException("Usuario no encontrado");
    }
  }

  public boolean updateUsuario(UUID id, InsertUsuario usuario) {
    Optional<Usuario> usuarioOpt = repository.findById(id);
    if (!usuarioOpt.isPresent()) {
      return false;
    }
    usuarioOpt.get().setNombre(usuario.nombre);
    usuarioOpt.get().setNombreUsuario(usuario.nombreUsuario);
    usuarioOpt.get().setContraseña(encryptPassword(usuario.contraseña));
    usuarioOpt.get().setRol(
        rolRepository.findByNombre(usuario.rol.isPresent() ? usuario.rol.get().name() : "OPERARIO").orElse(null));
    usuarioOpt.get().setCorreo(usuario.correo);
    usuarioOpt.get().setFechaModificacion(usuario.fechaModificacion);
    usuarioOpt.get().setActivo(usuario.activo);
    usuarioOpt.get().setFechaCreacion(usuario.fechaCreacion);
    usuarioOpt.get().setUltimoAcceso(null);
    repository.save(usuarioOpt.get());
    return true;
  }

  public boolean deleteUsuario(UUID id) {
    Optional<Usuario> usuarioOpt = repository.findById(id);
    if (!usuarioOpt.isPresent()) {
      return false;
    }
    repository.deleteById(id);
    return true;
  }
}

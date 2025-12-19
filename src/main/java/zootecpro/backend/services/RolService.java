package zootecpro.backend.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import zootecpro.backend.models.Rol;
import zootecpro.backend.repositories.RolRepository;

@Service
public class RolService {
  private final RolRepository rolRepository;

  public RolService(RolRepository rolRepository) {
    this.rolRepository = rolRepository;
    if (rolRepository.count() > 1) {
      return;
    }
    List<Rol> roles = List.of(
        Rol.builder().id(UUID.randomUUID())
            .nombre("ADMIN")
            .permisos(List.of("READ_Reproduccion", "WRITE_Reproduccion", "UPDATE_Reproduccion", "DELETE_Reproduccion",
                "READ_Crecimiento", "WRITE_Crecimiento", "UPDATE_Crecimiento", "DELETE_Crecimiento",
                "READ_Sanidad", "WRITE_Sanidad", "UPDATE_Sanidad", "DELETE_Sanidad",
                "WRITE_Establo",
                "READ_Enfermedades",
                "READ_Tratamientos"))
            .build(),
        Rol.builder().id(UUID.randomUUID())
            .nombre("VETERINARIO")
            .permisos(List.of("READ_Reproduccion", "WRITE_Reproduccion", "UPDATE_Reproduccion",
                "READ_Crecimiento", "WRITE_Crecimiento",
                "WRITE_Sanidad", "READ_Sanidad", "UPDATE_Sanidad",
                "READ_Enfermedades", "READ_Tratamientos"))
            .build(),
        Rol.builder().id(UUID.randomUUID())
            .nombre("OPERARIO")
            .permisos(List.of("READ_Reproduccion", "WRITE_Crecimiento", "READ_Crecimiento",
                "READ_Sanidad", "READ_Establo", "READ_Enfermedades", "READ_Tratamientos"))
            .build());
    rolRepository.saveAll(roles);
  }

  public boolean insertRol(String nombre, List<String> permisos) {
    if (this.rolRepository.findByNombre(nombre).isPresent()) {
      return false;
    }
    this.rolRepository.save(Rol.builder().id(UUID.randomUUID()).nombre(nombre).permisos(permisos).build());
    return true;
  }

  public List<String> getRolesName() {
    return this.rolRepository.findAllNombres();
  }

  public List<Rol> getRoles() {
    return this.rolRepository.findAll();
  }

  public boolean updateRol(UUID id, Rol newRol) {
    var rolOpt = this.rolRepository.findById(id);
    if (!rolOpt.isPresent()) {
      return false;
    }
    var rol = rolOpt.get();
    if (!newRol.getNombre().isEmpty()) {
      rol.setNombre(newRol.getNombre());
    }
    if (!newRol.getPermisos().isEmpty()) {
      rol.setPermisos(newRol.getPermisos());
    }
    this.rolRepository.save(rol);
    return true;
  }
}

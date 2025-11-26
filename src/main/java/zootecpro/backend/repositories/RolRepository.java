package zootecpro.backend.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import zootecpro.backend.models.Rol;

public interface RolRepository extends JpaRepository<Rol, UUID> {
  Optional<Rol> findByNombre(String nombre);
}

package zootecpro.backend.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import zootecpro.backend.models.establo.Establo;

public interface EstabloRepository extends JpaRepository<Establo, UUID> {
  Optional<Establo> findByNombre(String nombre);
}

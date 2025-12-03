package zootecpro.backend.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zootecpro.backend.models.Licencia;

@Repository
public interface LicenciaRepository extends JpaRepository<Licencia, UUID> {
  Optional<Licencia> findByNombre(String nombre);
}

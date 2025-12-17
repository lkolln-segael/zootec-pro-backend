package zootecpro.backend.repositories.sanidad;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import zootecpro.backend.models.enfermedad.TipoEnfermedad;

public interface TipoEnfermedadRepository extends JpaRepository<TipoEnfermedad, UUID> {
  Boolean existsByNombre(String nombre);
}

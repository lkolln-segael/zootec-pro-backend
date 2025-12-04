package zootecpro.backend.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import zootecpro.backend.models.enfermedad.Enfermedad;

public interface EnfermedadRepository extends JpaRepository<Enfermedad, UUID> {
}

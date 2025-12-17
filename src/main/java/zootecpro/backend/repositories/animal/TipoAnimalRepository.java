package zootecpro.backend.repositories.animal;

import java.util.UUID;

import zootecpro.backend.models.establo.TipoAnimal;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoAnimalRepository extends JpaRepository<TipoAnimal, UUID> {
  Boolean existsByNombre(String nombre);
}

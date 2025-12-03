package zootecpro.backend.repositories;

import java.util.UUID;

import zootecpro.backend.models.establo.Animal;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, UUID> {

}

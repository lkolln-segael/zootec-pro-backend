package zootecpro.backend.repositories.animal;

import java.util.List;
import java.util.UUID;

import zootecpro.backend.models.establo.Animal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnimalRepository extends JpaRepository<Animal, UUID> {
}

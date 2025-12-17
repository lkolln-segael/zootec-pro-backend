package zootecpro.backend.repositories.sanidad;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import zootecpro.backend.models.enfermedad.Enfermedad;

public interface EnfermedadRepository extends JpaRepository<Enfermedad, UUID> {
  @Query("SELECT e FROM Enfermedad e JOIN FETCH e.animal")
  List<Enfermedad> findAllEnfermedadesConAnimal();
}

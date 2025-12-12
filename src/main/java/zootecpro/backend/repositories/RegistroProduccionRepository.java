package zootecpro.backend.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import zootecpro.backend.models.registros.RegistroProduccion;

@Repository
public interface RegistroProduccionRepository extends JpaRepository<RegistroProduccion, UUID> {
  @Query("SELECT r from RegistroProduccion r JOIN FETCH r.animal")
  List<RegistroProduccion> findAllRegistrosConAnimal();
}

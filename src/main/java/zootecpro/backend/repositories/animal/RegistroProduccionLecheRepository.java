package zootecpro.backend.repositories.animal;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import zootecpro.backend.models.registros.RegistroProduccionLeche;

@Repository
public interface RegistroProduccionLecheRepository extends JpaRepository<RegistroProduccionLeche, UUID> {
  @Query("SELECT r from RegistroProduccionLeche r JOIN FETCH r.animal")
  List<RegistroProduccionLeche> findAllRegistrosConAnimal();
}

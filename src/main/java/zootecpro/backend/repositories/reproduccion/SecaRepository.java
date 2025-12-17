package zootecpro.backend.repositories.reproduccion;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zootecpro.backend.models.registros.Seca;

@Repository
public interface SecaRepository extends JpaRepository<Seca, UUID> {

}

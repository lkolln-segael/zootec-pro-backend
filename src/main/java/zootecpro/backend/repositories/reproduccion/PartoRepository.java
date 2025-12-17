package zootecpro.backend.repositories.reproduccion;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zootecpro.backend.models.registros.Parto;

@Repository
public interface PartoRepository extends JpaRepository<Parto, UUID> {

}

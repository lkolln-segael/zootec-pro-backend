package zootecpro.backend.repositories.sanidad;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zootecpro.backend.models.enfermedad.TipoTratamiento;

@Repository
public interface TipoTratamientoRepository extends JpaRepository<TipoTratamiento, UUID> {

}

package zootecpro.backend.repositories.reproduccion;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zootecpro.backend.models.registros.Prenez;

@Repository
public interface PrenezRepository extends JpaRepository<Prenez, UUID> {

}

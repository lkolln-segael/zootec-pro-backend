package zootecpro.backend.models.enfermedad;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
public class TipoTratamiento {
  @Id
  private UUID id;
  private String nombre;
}

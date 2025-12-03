package zootecpro.backend.models.enfermedad;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TipoTratamiento {
  @Id
  private UUID id;
  private String nombre;
  private String descripcion;
}

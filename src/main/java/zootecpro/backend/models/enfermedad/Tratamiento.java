package zootecpro.backend.models.enfermedad;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Tratamiento {
  @Id
  private UUID id;
  private String nombre;
  @OneToOne
  private TipoTratamiento tipoTratamiento;
}

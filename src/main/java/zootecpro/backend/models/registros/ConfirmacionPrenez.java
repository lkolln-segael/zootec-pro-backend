package zootecpro.backend.models.registros;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmacionPrenez {
  @Id
  private UUID id;
  private TipoPrenez tipo;
  @OneToOne
  private RegistroReproduccion registroReproduccion;
}

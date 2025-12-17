package zootecpro.backend.models.registros;

import java.time.LocalDate;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Parto {
  @Id
  private UUID id;
  private String numero;
  @OneToOne
  private RegistroReproduccion registroReproduccion;
}

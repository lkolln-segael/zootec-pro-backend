package zootecpro.backend.models.registros;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seca {
  @Id
  private UUID id;
  private String motivo;
  @OneToOne
  private RegistroReproduccion registroReproduccion;
}

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
import zootecpro.backend.models.establo.Animal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prenez {
  @Id
  private UUID id;
  private LocalDate fechaCelo;
  private LocalDate fechaInseminacion;
  private LocalDate fechaDiagnostico;
  @OneToOne
  private Animal padre;
  @OneToOne
  private Animal madre;
  @OneToOne
  private RegistroReproduccion registroReproduccion;
}

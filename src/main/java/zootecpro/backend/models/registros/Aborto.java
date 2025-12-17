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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Aborto {
  @Id
  private UUID id;
  private String tipo;
  private LocalDate fechaRegistro;
  @OneToOne
  private RegistroReproduccion registroReproduccion;
}

package zootecpro.backend.models.registros;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zootecpro.backend.models.establo.Animal;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegistroProduccion {
  @Id
  private UUID id;
  private Double pesoLeche;
  private Double ureaLeche;
  private Double phLeche;
  private LocalDateTime fechaRegistro;
  private Double aflatoxinas;
  @ManyToOne(fetch = FetchType.LAZY)
  private Animal animal;
}

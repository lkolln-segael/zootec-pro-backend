package zootecpro.backend.models;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Licencia {
  @Id
  private UUID id;
  private String nombre;
  private Integer limiteAnimales;
  private Double costo;
  private LocalDate fechaInicio;
  private LocalDate fechaFinal;
  @Builder.Default
  private LocalDate fechaCreacion = LocalDate.now();
  private LocalDate fechaModificacion;
}

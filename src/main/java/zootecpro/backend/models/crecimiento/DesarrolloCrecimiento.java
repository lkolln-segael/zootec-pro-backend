package zootecpro.backend.models.crecimiento;

import java.time.LocalDateTime;
import java.util.UUID;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DesarrolloCrecimiento {
  @Id
  private UUID id;
  private String estado;
  private LocalDateTime fechaRegistro;
  private Integer pesoActual;
  private Double tamaño;
  private String condicionCorporal;
  private String unidadesAnimal;
}

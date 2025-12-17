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
public class RegistroNacimiento {
  @Id
  private UUID id;
  private String observacionesNacimiento;
  private Float pesoNacimiento;
  private Float altitud;
  private String ubicacion;
  private LocalDate fecha;
  private Float temperatura;
  @OneToOne
  private Animal animal;

  @OneToOne
  private RegistroReproduccion registroReproduccion;
}
